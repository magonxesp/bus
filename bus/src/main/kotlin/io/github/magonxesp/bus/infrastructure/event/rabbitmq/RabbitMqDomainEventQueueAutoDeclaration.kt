package io.github.magonxesp.bus.infrastructure.event.rabbitmq

import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import io.github.magonxesp.bus.domain.event.DomainEventClass
import io.github.magonxesp.bus.domain.event.DomainEventRegistry
import io.github.magonxesp.bus.domain.event.DomainEventSubscriberClass
import org.slf4j.LoggerFactory

class RabbitMqDomainEventQueueAutoDeclaration(
	private val connection: Connection,
	private val queueResolver: RabbitMqDomainEventQueueResolver,
	private val registry: DomainEventRegistry
) {
	private val logger = LoggerFactory.getLogger(this::class.java)

	/**
	 * Create the dead letter queue.
	 *
	 * @return The map with the arguments to send failed messages to this dead letter
	 */
	private fun Channel.createDeadLetterQueue(event: DomainEventClass, subscriber: DomainEventSubscriberClass): Map<String, String> {
		val exchange = queueResolver.domainEventDeadLetterExchangeName(event)
		val queue = queueResolver.domainEventDeadLetterQueueName(subscriber)
		val routingKey = queueResolver.domainEventDeadLetterRoutingKey(subscriber)

		exchangeDeclare(exchange, "direct", true)
		queueDeclare(queue, true, false, false, null)
		queueBind(queue, exchange, routingKey)

		return mapOf(
			"x-dead-letter-exchange" to exchange,
			"x-dead-letter-routing-key" to routingKey
		)
	}

	private fun Channel.createQueue(event: DomainEventClass, subscriber: DomainEventSubscriberClass) {
		val deadLetterArgs = createDeadLetterQueue(event, subscriber)

		val exchange = queueResolver.domainEventExchangeName(event)
		val queue = queueResolver.domainEventQueueName(subscriber)
		val routingKey = queueResolver.domainEventRoutingKey(subscriber)

		exchangeDeclare(exchange, "fanout", true)
		queueDeclare(queue, true, false, false, deadLetterArgs)
		queueBind(queue, exchange, routingKey)
	}

	fun declareAllQueues() {
		logger.info("Declaring all Domain Event subscribers RabbitMq queues")
		val channel = connection.createChannel()

		registry.domainEventSubscribers().forEach { entry ->
			entry.value.forEach { subscriber ->
				channel.createQueue(entry.key, subscriber)
			}
		}

		logger.info("All Domain Event subscribers queues declared!")
	}
}
