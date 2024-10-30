package io.github.magonxesp.bus.infrastructure.command.rabbitmq

import com.rabbitmq.client.*
import io.github.magonxesp.bus.domain.command.CommandConsumer
import io.github.magonxesp.bus.domain.command.CommandRegistry
import io.github.magonxesp.bus.infrastructure.command.CommandExecutor
import io.github.magonxesp.bus.infrastructure.command.CommandSerializer
import org.slf4j.LoggerFactory
import kotlin.concurrent.thread

class RabbitMqCommandConsumer(
	private val connection: Connection,
	private val commandRegistry: CommandRegistry,
	private val commandExecutor: CommandExecutor,
	private val commandSerializer: CommandSerializer,
	private val commandQueueResolver: RabbitMqCommandQueueResolver
) : CommandConsumer {
	private val logger = LoggerFactory.getLogger(this::class.java)
	private val consumers = mutableListOf<String>()

	@get:Synchronized
	@set:Synchronized
	private var isRunning = false

	@get:Synchronized
	@set:Synchronized
	private var isConsuming = false

	private fun createConsumer(channel: Channel) = object : DefaultConsumer(channel) {
		override fun handleDelivery(consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties?, body: ByteArray?) {

			val deliveryTag = envelope?.deliveryTag ?: return
			val commandJson = body?.decodeToString() ?: return
			logger.debug("Attempting to consume incoming command: $commandJson")

			val command = commandSerializer.deserialize(commandJson)
			isConsuming = true

			commandExecutor.execute(command).onSuccess {
				channel.basicAck(deliveryTag, false)
				isConsuming = false
			}.onFailure {
				channel.basicReject(deliveryTag, false)
				isConsuming = false
			}
		}
	}

	private fun blockThread() {
		isRunning = true
		while (isRunning) {
			Thread.sleep(100)
		}
	}

	private fun setupGracefulHook(channel: Channel) {
		Runtime.getRuntime().addShutdownHook(thread(start = false) {
			isRunning = false
			consumers.forEach { consumer -> channel.basicCancel(consumer) }

			while (isConsuming) {
				logger.info("Consumers are finishing their work, waiting for they finish...")
				Thread.sleep(100)
			}
		})
	}

	override fun startConsume(block: Boolean) {
		val channel = connection.createChannel()
		val commandHandlers = commandRegistry.commandHandlers().entries

		for ((_, handler) in commandHandlers) {
			val deliveryHandler = createConsumer(channel)
			val consumer = channel.basicConsume(commandQueueResolver.commandQueueName(handler), false, deliveryHandler)
			consumers.add(consumer)
		}

		setupGracefulHook(channel)
		if (block) blockThread()
	}
}
