package io.github.magonxesp.bus.infrastructure.event.koin

import io.github.magonxesp.bus.domain.event.DomainEventBus
import io.github.magonxesp.bus.infrastructure.event.inmemory.InMemoryAsyncDomainEventBus
import io.github.magonxesp.bus.infrastructure.event.inmemory.InMemorySyncDomainEventBus
import io.github.magonxesp.bus.infrastructure.shared.koin.BusConfiguration
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

class InMemoryDomainEventBusConfiguration : BusConfiguration() {
	var async: Boolean = false
	var asyncMaxQueueItems: Int = 100
}

fun inMemoryDomainEventModule(configure: InMemoryDomainEventBusConfiguration.() -> Unit): Module = module {
	val configuration = InMemoryDomainEventBusConfiguration().apply { configure() }

	commonDomainEventDependencies()
	domainEventRegistryModule(configuration)
	domainEventImplementation(configuration)
}

fun Module.domainEventImplementation(configuration: InMemoryDomainEventBusConfiguration) {
	if (configuration.async) {
		single { InMemoryAsyncDomainEventBus(get(), get(), configuration.asyncMaxQueueItems) } bind DomainEventBus::class
	} else {
		single { InMemorySyncDomainEventBus(get()) } bind DomainEventBus::class
	}
}
