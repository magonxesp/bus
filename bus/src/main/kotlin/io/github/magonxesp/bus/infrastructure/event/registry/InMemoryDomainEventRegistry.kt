package io.github.magonxesp.bus.infrastructure.event.registry

import io.github.magonxesp.bus.domain.event.*
import kotlin.reflect.full.isSubclassOf

class InMemoryDomainEventRegistry : DomainEventRegistry {
	var registry: MutableMap<DomainEventClass, MutableSet<DomainEventSubscriberClass>> = mutableMapOf()
		internal set

	fun addSubscriber(domainEvent: DomainEventClass, domainEventSubscriber: DomainEventSubscriberClass) {
		if (!domainEvent.isSubclassOf(DomainEvent::class)) {
			throw RuntimeException("The $domainEvent must be a subclass of ${DomainEvent::class}")
		}

		if (!domainEventSubscriber.isSubclassOf(DomainEventSubscriber::class)) {
			throw RuntimeException("The $domainEventSubscriber must be a subclass of ${DomainEventSubscriber::class}")
		}

		val subscribers = registry[domainEvent] ?: mutableSetOf<DomainEventSubscriberClass>().also {
			registry[domainEvent] = it
		}

		subscribers.add(domainEventSubscriber)
	}

	override fun domainEventSubscribers() = registry
}
