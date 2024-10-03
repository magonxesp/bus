package io.github.magonxesp.bus.domain.event

interface DomainEventBus {
	fun publish(vararg domainEvent: DomainEvent)
}
