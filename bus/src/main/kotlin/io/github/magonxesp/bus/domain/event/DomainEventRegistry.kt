package io.github.magonxesp.bus.domain.event

interface DomainEventRegistry {
	fun domainEventSubscribers(): Map<DomainEventClass, Set<DomainEventSubscriberClass>>
}
