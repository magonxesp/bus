package io.github.magonxesp.bus.infrastructure.event.koin

import io.github.magonxesp.bus.domain.event.DomainEventBus
import io.github.magonxesp.bus.domain.event.DomainEventRegistry
import io.github.magonxesp.bus.domain.event.DomainEventSubscribers
import io.github.magonxesp.bus.infrastructure.event.InMemoryDomainEventRegistry
import io.github.magonxesp.bus.infrastructure.event.ReflectionDomainEventRegistry
import io.github.magonxesp.bus.infrastructure.event.inmemory.InMemorySyncDomainEventBus
import io.github.magonxesp.bus.infrastructure.shared.dependencyinjection.BusDependencyInjectionHelper
import io.github.magonxesp.bus.infrastructure.shared.koin.KoinDependencyInjectionHelper
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

fun inMemoryDomainEventBusModule(basePackage: String): Module {
	return module {
		single { KoinDependencyInjectionHelper() } bind BusDependencyInjectionHelper::class
		single { ReflectionDomainEventRegistry(basePackage) } bind DomainEventRegistry::class
		single { InMemorySyncDomainEventBus(get(), get()) } bind DomainEventBus::class
	}
}

fun inMemoryCommandBusModule(
	subscribers: DomainEventSubscribers,
): Module {
	val registry = InMemoryDomainEventRegistry().apply {
		registry = subscribers
			.mapValues { it.value.toMutableSet() }
			.toMutableMap()
	}

	return module {
		single { KoinDependencyInjectionHelper() } bind BusDependencyInjectionHelper::class
		single { registry } bind DomainEventRegistry::class
		single { InMemorySyncDomainEventBus(get(), get()) } bind DomainEventBus::class
	}
}
