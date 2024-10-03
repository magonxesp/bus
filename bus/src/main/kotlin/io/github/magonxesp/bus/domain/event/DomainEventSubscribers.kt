package io.github.magonxesp.bus.domain.event

import kotlin.reflect.KClass

/**
 * The map of available domain event subscribers.
 *
 * The key is the domain event class and the value is the list of subscribers class listening
 * to the domain event.
 */
typealias DomainEventSubscribers = Map<KClass<*>, Set<KClass<*>>>
