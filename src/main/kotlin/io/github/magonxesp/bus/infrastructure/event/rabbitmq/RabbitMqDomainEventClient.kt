package io.github.magonxesp.bus.infrastructure.event.rabbitmq

import com.rabbitmq.client.Channel
import io.github.magonxesp.bus.domain.event.DomainEvent
import io.github.magonxesp.bus.domain.shared.camelToSnakeCase
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqClient
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConfiguration
import java.util.*
import kotlin.reflect.KClass

abstract class RabbitMqDomainEventClient(configuration: RabbitMqConfiguration) : RabbitMqClient(configuration) {
	protected val KClass<*>.subscriberQueueName: String
		get() = (simpleName?.camelToSnakeCase() ?: "anonymous_subscriber_${UUID.randomUUID()}")
			.prefixedQueueName()

	protected val DomainEvent.exchangeName: String
		get() = "domain_event.$eventName"

	protected val DomainEvent.deadLetterExchangeName: String
		get() = "$exchangeName.dlx"

	protected val KClass<*>.deadLetterSubscriberQueueName: String
		get() = "$subscriberQueueName.dlx"

	protected val KClass<*>.routingKey: String
		get() = "domain_event_subscriber.$subscriberQueueName"

	protected val KClass<*>.deadLetterRoutingKey: String
		get() = "$routingKey.dlx"

	/**
	 * Create the dead letter queue.
	 *
	 * @return The map with the arguments to send failed messages to this dead letter
	 */
	private fun Channel.createDeadLetterQueue(
		domainEvent: DomainEvent,
		subscriberClass: KClass<*>
	): Map<String, String> {
		exchangeDeclare(domainEvent.deadLetterExchangeName, "direct", true)
		queueDeclare(subscriberClass.deadLetterSubscriberQueueName, true, false, false, null)
		queueBind(
			subscriberClass.deadLetterSubscriberQueueName,
			domainEvent.deadLetterExchangeName,
			subscriberClass.deadLetterRoutingKey
		)

		return mapOf(
			"x-dead-letter-exchange" to domainEvent.deadLetterExchangeName,
			"x-dead-letter-routing-key" to subscriberClass.deadLetterRoutingKey
		)
	}

	protected fun Channel.createQueue(
		domainEvent: DomainEvent,
		subscriberClass: KClass<*>
	) {
		val deadLetterArgs = createDeadLetterQueue(domainEvent, subscriberClass)

		exchangeDeclare(domainEvent.exchangeName, "fanout", true)
		queueDeclare(subscriberClass.subscriberQueueName, true, false, false, deadLetterArgs)
		queueBind(subscriberClass.subscriberQueueName, domainEvent.exchangeName, subscriberClass.routingKey)
	}
}
