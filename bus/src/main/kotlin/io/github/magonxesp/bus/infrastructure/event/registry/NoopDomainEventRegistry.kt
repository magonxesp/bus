package io.github.magonxesp.bus.infrastructure.event.registry

import io.github.magonxesp.bus.domain.event.DomainEventClass
import io.github.magonxesp.bus.domain.event.DomainEventRegistry
import io.github.magonxesp.bus.domain.event.DomainEventSubscriberClass

class NoopDomainEventRegistry : DomainEventRegistry {
	override fun domainEventSubscribers(): Map<DomainEventClass, Set<DomainEventSubscriberClass>> = mapOf()
}
