package io.github.magonxesp.bus.infrastructure.command.rabbitmq

import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import io.github.magonxesp.bus.domain.command.CommandHandler
import io.github.magonxesp.bus.domain.command.CommandHandlerClass
import io.github.magonxesp.bus.domain.command.CommandRegistry
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

class RabbitMqCommandQueueAutoDeclaration(
	private val connection: Connection,
	private val queueResolver: RabbitMqCommandQueueResolver,
	private val registry: CommandRegistry
) {
	private val logger = LoggerFactory.getLogger(this::class.java)

	/**
	 * Create the dead letter queue.
	 *
	 * @return The map with the arguments to send failed messages to this dead letter
	 */
	private fun Channel.createDeadLetterQueue(commandHandler: CommandHandlerClass): Map<String, String> {
		val exchange = queueResolver.commandDeadLetterExchangeName(commandHandler)
		val queue = queueResolver.commandDeadLetterQueueName(commandHandler)
		val routingKey = queueResolver.commandDeadLetterRoutingKey(commandHandler)

		exchangeDeclare(exchange, "direct", true)
		queueDeclare(queue, true, false, false, null)
		queueBind(queue, exchange, routingKey)

		return mapOf(
			"x-dead-letter-exchange" to exchange,
			"x-dead-letter-routing-key" to routingKey
		)
	}

	private fun Channel.createQueue(commandHandler: CommandHandlerClass) {
		val deadLetterArgs = createDeadLetterQueue(commandHandler)

		val exchange = queueResolver.commandExchangeName(commandHandler)
		val queue = queueResolver.commandQueueName(commandHandler)
		val routingKey = queueResolver.commandRoutingKey(commandHandler)

		exchangeDeclare(exchange, "direct", true)
		queueDeclare(queue, true, false, false, deadLetterArgs)
		queueBind(queue, exchange, routingKey)
	}

	fun declareAllQueues() {
		logger.info("Declaring all command RabbitMq queues")
		val channel = connection.createChannel()

		registry.commandHandlers().forEach { entry ->
			channel.createQueue(entry.value)
		}

		logger.info("All command queues declared!")
	}
}
