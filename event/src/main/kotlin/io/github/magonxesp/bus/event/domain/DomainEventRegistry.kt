package io.github.magonxesp.bus.event.domain

import kotlin.reflect.KClass

interface DomainEventRegistry {
	/**
	 * Get the domain event subscribers
	 */
	fun domainEventSubscribers(): Map<KClass<*>, Set<KClass<*>>>
}
