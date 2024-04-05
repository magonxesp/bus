package io.github.magonxesp.bus.infrastructure.event

import io.github.magonxesp.bus.domain.event.DomainEvent
import io.github.magonxesp.bus.domain.event.DomainEventRegistry
import io.github.magonxesp.bus.domain.event.DomainEventSubscriber
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

class InMemoryDomainEventRegistry : DomainEventRegistry {
	private val registry: MutableMap<KClass<*>, MutableSet<KClass<*>>> = mutableMapOf()

	fun addSubscriber(domainEvent: KClass<*>, domainEventSubscriber: KClass<*>) {
		if (!domainEvent.isSubclassOf(DomainEvent::class)) {
			throw RuntimeException("The $domainEvent must be a subclass of ${DomainEvent::class}")
		}

		if (!domainEventSubscriber.isSubclassOf(DomainEventSubscriber::class)) {
			throw RuntimeException("The $domainEventSubscriber must be a subclass of ${DomainEventSubscriber::class}")
		}

		val subscribers = registry[domainEvent]

		if (subscribers == null) {
			registry[domainEvent] = mutableSetOf()
		}

		registry[domainEvent]!!.add(domainEventSubscriber)
	}

	override fun domainEventSubscribers(): Map<KClass<*>, Set<KClass<*>>> = registry
}
