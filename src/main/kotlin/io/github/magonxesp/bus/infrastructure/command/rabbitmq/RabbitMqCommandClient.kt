package io.github.magonxesp.bus.infrastructure.command.rabbitmq

import com.rabbitmq.client.Channel
import io.github.magonxesp.bus.domain.command.Command
import io.github.magonxesp.bus.domain.event.DomainEvent
import io.github.magonxesp.bus.domain.shared.camelToSnakeCase
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqClient
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConfiguration
import java.util.*
import kotlin.reflect.KClass

abstract class RabbitMqCommandClient(configuration: RabbitMqConfiguration) : RabbitMqClient(configuration) {
	protected val KClass<*>.handlerQueueName: String
		get() = (simpleName?.camelToSnakeCase() ?: "anonymous_handler_${UUID.randomUUID()}")
			.prefixedQueueName()

	protected val exchangeName: String = "command"
	protected val deadLetterExchangeName: String = "$exchangeName.dlx"

	protected val KClass<*>.deadLetterHandlerQueueName: String
		get() = "$handlerQueueName.dlx"

	protected val KClass<*>.routingKey: String
		get() = "command.$handlerQueueName"

	protected val KClass<*>.deadLetterRoutingKey: String
		get() = "$routingKey.dlx"

	/**
	 * Create the dead letter queue.
	 *
	 * @return The map with the arguments to send failed messages to this dead letter
	 */
	private fun Channel.createDeadLetterQueue(commandHandler: KClass<*>): Map<String, String> {
		exchangeDeclare(deadLetterExchangeName, "direct", true)
		queueDeclare(commandHandler.deadLetterHandlerQueueName, true, false, false, null)
		queueBind(
			commandHandler.deadLetterHandlerQueueName,
			deadLetterExchangeName,
			commandHandler.deadLetterRoutingKey
		)

		return mapOf(
			"x-dead-letter-exchange" to deadLetterExchangeName,
			"x-dead-letter-routing-key" to commandHandler.deadLetterRoutingKey
		)
	}

	protected fun Channel.createQueue(commandHandler: KClass<*>) {
		val deadLetterArgs = createDeadLetterQueue(commandHandler)

		exchangeDeclare(exchangeName, "direct", true)
		queueDeclare(commandHandler.handlerQueueName, true, false, false, deadLetterArgs)
		queueBind(commandHandler.handlerQueueName, exchangeName, commandHandler.routingKey)
	}
}
