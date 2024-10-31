package io.github.magonxesp.bus.infrastructure.event.inmemory

import io.github.magonxesp.bus.domain.event.DomainEvent
import io.github.magonxesp.bus.domain.event.DomainEventBus
import io.github.magonxesp.bus.domain.event.DomainEventRegistry
import io.github.magonxesp.bus.domain.event.DomainEventSubscriber
import io.github.magonxesp.bus.infrastructure.event.DomainEventExecutor
import io.github.magonxesp.bus.infrastructure.event.throwOnFirstFailure
import io.github.magonxesp.bus.infrastructure.shared.dependencyinjection.BusDependencyInjectionHelper

class InMemorySyncDomainEventBus(private val domainEventExecutor: DomainEventExecutor) : DomainEventBus {
	override fun publish(vararg domainEvent: DomainEvent<*>) {
		for (event in domainEvent) {
			domainEventExecutor.execute(event).throwOnFirstFailure()
		}
	}
}
