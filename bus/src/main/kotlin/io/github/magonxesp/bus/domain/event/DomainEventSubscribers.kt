package io.github.magonxesp.bus.domain.event

import kotlin.reflect.KClass

typealias DomainEventClass = KClass<out DomainEvent<*>>
typealias DomainEventSubscriberClass = KClass<out DomainEventSubscriber<*>>
typealias DomainEventSubscribers = Map<DomainEventClass, Set<DomainEventSubscriberClass>>
