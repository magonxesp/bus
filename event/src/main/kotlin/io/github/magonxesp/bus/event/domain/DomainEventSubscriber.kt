package io.github.magonxesp.bus.event.domain

interface DomainEventSubscriber<E : DomainEvent> {
	fun handle(event: E)
}
