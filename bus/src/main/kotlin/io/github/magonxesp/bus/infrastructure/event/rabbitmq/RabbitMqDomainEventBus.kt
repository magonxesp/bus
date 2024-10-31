package io.github.magonxesp.bus.infrastructure.event.rabbitmq

import com.rabbitmq.client.Connection
import io.github.magonxesp.bus.domain.event.DomainEvent
import io.github.magonxesp.bus.domain.event.DomainEventBus
import io.github.magonxesp.bus.infrastructure.event.DomainEventSerializer

class RabbitMqDomainEventBus(
	private val connection: Connection,
	private val domainEventSerializer: DomainEventSerializer,
	private val domainEventQueueResolver: RabbitMqDomainEventQueueResolver
) : DomainEventBus {
	override fun publish(vararg domainEvent: DomainEvent<*>) {
		val channel = connection.createChannel()

		for (event in domainEvent) {
			val exchange = domainEventQueueResolver.domainEventExchangeName(event::class)
			val serialized = domainEventSerializer.serialize(event)

			channel.basicPublish(exchange, "", null, serialized.toByteArray())
		}
	}
}
