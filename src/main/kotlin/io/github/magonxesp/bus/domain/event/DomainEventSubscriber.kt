package io.github.magonxesp.bus.domain.event

interface DomainEventSubscriber<E : DomainEvent> {
	fun handle(event: E)
}
