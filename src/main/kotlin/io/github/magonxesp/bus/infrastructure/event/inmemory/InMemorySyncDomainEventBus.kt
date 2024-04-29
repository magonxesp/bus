package io.github.magonxesp.bus.infrastructure.event.inmemory

import io.github.magonxesp.bus.domain.event.DomainEvent
import io.github.magonxesp.bus.domain.event.DomainEventBus
import io.github.magonxesp.bus.domain.event.DomainEventRegistry
import io.github.magonxesp.bus.domain.event.DomainEventSubscriber
import io.github.magonxesp.bus.infrastructure.shared.dependencyinjection.BusDependencyInjectionHelper

class InMemorySyncDomainEventBus(
	private val registry: DomainEventRegistry,
	private val dependencyInjectionHelper: BusDependencyInjectionHelper
) : DomainEventBus {
	override fun publish(vararg domainEvent: DomainEvent) {
		val domainEventSubscribers = registry.domainEventSubscribers()

		for (event in domainEvent) {
			val subscribers = domainEventSubscribers[event::class] ?: setOf()

			for (subscriber in subscribers) {
				val subscriberInstance = dependencyInjectionHelper.get<DomainEventSubscriber<DomainEvent>>(subscriber)
				subscriberInstance.handle(event)
			}
		}
	}
}
