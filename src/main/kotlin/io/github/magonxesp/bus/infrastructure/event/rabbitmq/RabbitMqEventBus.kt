package io.github.magonxesp.bus.infrastructure.event.rabbitmq

import io.github.magonxesp.bus.domain.event.DomainEvent
import io.github.magonxesp.bus.domain.event.DomainEventBus
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConnectionFactory

class RabbitMqEventBus(private val connectionFactory: RabbitMqConnectionFactory) : DomainEventBus {
	override fun publish(vararg domainEvent: DomainEvent) {

	}
}
