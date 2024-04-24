package io.github.magonxesp.bus.infrastructure.command.rabbitmq

import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Channel
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope
import io.github.magonxesp.bus.domain.command.Command
import io.github.magonxesp.bus.domain.command.CommandHandler
import io.github.magonxesp.bus.domain.command.CommandRegistry
import io.github.magonxesp.bus.domain.event.DomainEventConsumer
import io.github.magonxesp.bus.infrastructure.command.deserializeCommand
import io.github.magonxesp.bus.infrastructure.shared.dependencyinjection.BusDependencyInjectionHelper
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConfiguration
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConnectionFactory
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

class RabbitMqCommandConsumer(
	private val registry: CommandRegistry,
	private val dependencyInjectionHelper: BusDependencyInjectionHelper,
	private val connectionFactory: RabbitMqConnectionFactory,
	configuration: RabbitMqConfiguration = RabbitMqConfiguration()
) : RabbitMqCommandClient(configuration), DomainEventConsumer {
	class HandlerConsumer(
		channel: Channel,
		private val commandClass: KClass<*>,
		private val commandHandler: CommandHandler<Command>
	) : DefaultConsumer(channel) {
		private val logger = LoggerFactory.getLogger(this::class.java)

		override fun handleDelivery(consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties?, body: ByteArray?) {
			val deliveryTag = envelope?.deliveryTag ?: return

			try {
				val commandJson = body?.decodeToString() ?: return
				val command = commandJson.deserializeCommand(commandClass)
				commandHandler.handle(command)
				channel.basicAck(deliveryTag, false)
			} catch (exception: Exception) {
				logger.warn("${exception::class}: ${exception.message}")
				channel.basicReject(deliveryTag, false)
			}
		}
	}

	override fun startConsume(block: Boolean) {
		val connection = connectionFactory.getConnection()
		val channel = connection.createChannel()
		val commandHandlers = registry.commandHandlers().entries
		val consumers = mutableListOf<String>()
		var stop = false

		for ((commandClass, commandHandlerClass) in commandHandlers) {
			val commandHandler = dependencyInjectionHelper.get<CommandHandler<Command>>(commandHandlerClass)
			val consumer = HandlerConsumer(channel, commandClass, commandHandler)
			consumers.add(channel.basicConsume(commandHandlerClass.handlerQueueName, false, consumer))
		}

		connectionFactory.addShutdownTask {
			for (consumer in consumers) {
				channel.basicCancel(consumer)
			}

			channel.close()
			stop = true
		}

		while (block) {
			if (stop) break
		}
	}
}
