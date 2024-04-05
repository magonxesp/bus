package io.github.magonxesp.bus.domain.event

import kotlin.reflect.KClass

interface DomainEventRegistry {
	/**
	 * Get the domain event subscribers
	 */
	fun domainEventSubscribers(): Map<KClass<*>, Set<KClass<*>>>
}
