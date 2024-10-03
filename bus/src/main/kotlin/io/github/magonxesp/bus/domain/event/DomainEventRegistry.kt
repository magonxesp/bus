package io.github.magonxesp.bus.domain.event

interface DomainEventRegistry {
	/**
	 * Get the domain event subscribers
	 */
	fun domainEventSubscribers(): DomainEventSubscribers
}
