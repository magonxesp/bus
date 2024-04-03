package io.github.magonxesp.bus.event.infrastructure.rabbitmq

import io.github.magonxesp.bus.event.domain.DomainEvent
import io.github.magonxesp.bus.event.domain.DomainEventBus
import io.github.magonxesp.bus.core.infrastructure.rabbitmq.RabbitMqConnectionFactory

class RabbitMqEventBus(private val connectionFactory: RabbitMqConnectionFactory) : DomainEventBus {
	override fun publish(vararg domainEvent: DomainEvent) {

	}
}
