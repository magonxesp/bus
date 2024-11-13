package io.github.magonxesp.bus.infrastructure.command.rabbitmq

import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.GetResponse
import io.github.magonxesp.bus.domain.command.CommandConsumer
import io.github.magonxesp.bus.domain.command.CommandHandlerClass
import io.github.magonxesp.bus.domain.command.CommandRegistry
import io.github.magonxesp.bus.infrastructure.command.CommandExecutor
import io.github.magonxesp.bus.infrastructure.command.CommandSerializer
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.delayEach
import kotlinx.coroutines.flow.flow
import org.slf4j.LoggerFactory
import java.util.concurrent.Executors
import kotlin.concurrent.thread

class RabbitMqPollingCommandConsumer(
	private val connection: Connection,
	private val commandRegistry: CommandRegistry,
	private val commandExecutor: CommandExecutor,
	private val commandSerializer: CommandSerializer,
	private val commandQueueResolver: RabbitMqCommandQueueResolver
) : CommandConsumer {
	private val logger = LoggerFactory.getLogger(this::class.java)
	private val consumers = mutableListOf<Job>()

	private fun setupGracefulHook() {
		Runtime.getRuntime().addShutdownHook(thread(start = false) {
			logger.info("Stopping all command consumers...")
			consumers.forEach {
				it.cancel()
			}
		})
	}

	private fun processDelivery(channel: Channel, delivery: GetResponse) {
		val deliveryTag = delivery.envelope.deliveryTag
		val commandJson = delivery.body.decodeToString()
		logger.debug("Attempting to consume incoming command: $commandJson")

		val command = commandSerializer.deserialize(commandJson)

		commandExecutor.execute(command).onSuccess {
			channel.basicAck(deliveryTag, false)
		}.onFailure {
			channel.basicReject(deliveryTag, false)
		}
	}

	private fun pullCommandForHandler(channel: Channel, handler: CommandHandlerClass) = flow {
		val queue = commandQueueResolver.commandQueueName(handler)
		logger.debug("Pulling next 10 messages from queue: $queue")

		for (i in 0..10) {
			val delivery = channel.basicGet(queue, false) ?: return@flow
			emit(delivery)
		}
	}

	private suspend fun startPolling(channel: Channel, handler: CommandHandlerClass) = coroutineScope {
		while (isActive) {
			pullCommandForHandler(channel, handler).collect {
				processDelivery(channel, it)
			}

			logger.debug("Waiting for pull next messages...")
			delay(1000)
		}
	}

	private fun startPollingForAllHandlers() = runBlocking {
		val channel = connection.createChannel()
		val commandHandlers = commandRegistry.commandHandlers().entries

		for ((_, handler) in commandHandlers) {
			launch(Dispatchers.IO) {
				startPolling(channel, handler)
			}.also {
				consumers.add(it)
			}
		}
	}

	override fun startConsume(block: Boolean) {
		setupGracefulHook()

		if (block) {
			startPollingForAllHandlers()
		} else {
			thread { startPollingForAllHandlers() }
		}
	}
}
