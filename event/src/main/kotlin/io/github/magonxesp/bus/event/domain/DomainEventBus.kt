package io.github.magonxesp.bus.event.domain

interface DomainEventBus {
	fun publish(vararg domainEvent: DomainEvent)
}
