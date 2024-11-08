package io.github.magonxesp.bus.infrastructure.event.koin

import io.github.magonxesp.bus.domain.event.DomainEventBus
import io.github.magonxesp.bus.domain.event.DomainEventConsumer
import io.github.magonxesp.bus.infrastructure.event.inmemory.InMemoryAsyncDomainEventBus
import io.github.magonxesp.bus.infrastructure.event.inmemory.InMemorySyncDomainEventBus
import io.github.magonxesp.bus.infrastructure.event.inmemory.NoopDomainEventConsumer
import io.github.magonxesp.bus.infrastructure.shared.koin.BusConfiguration
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

class InMemoryDomainEventBusConfiguration : BusConfiguration() {
	var async: Boolean = false
	var asyncMaxThreads: Int = 4
}

fun inMemoryDomainEventModule(configure: InMemoryDomainEventBusConfiguration.() -> Unit): Module = module {
	val configuration = InMemoryDomainEventBusConfiguration().apply { configure() }

	commonDomainEventDependencies()
	domainEventRegistryModule(configuration)
	domainEventImplementation(configuration)
	single { NoopDomainEventConsumer() } bind DomainEventConsumer::class
}

fun Module.domainEventImplementation(configuration: InMemoryDomainEventBusConfiguration) {
	if (configuration.async) {
		single { InMemoryAsyncDomainEventBus(get(), get(), configuration.asyncMaxThreads) } bind DomainEventBus::class
	} else {
		single { InMemorySyncDomainEventBus(get()) } bind DomainEventBus::class
	}
}
