package io.github.magonxesp.bus.infrastructure.event.koin

import io.github.magonxesp.bus.domain.event.DomainEventRegistry
import io.github.magonxesp.bus.infrastructure.event.DomainEventExecutor
import io.github.magonxesp.bus.infrastructure.event.DomainEventSerializer
import io.github.magonxesp.bus.infrastructure.event.registry.NoopDomainEventRegistry
import io.github.magonxesp.bus.infrastructure.event.registry.ReflectionDomainEventRegistry
import io.github.magonxesp.bus.infrastructure.shared.koin.BusConfiguration
import io.github.magonxesp.bus.infrastructure.shared.koin.defaultDependencyInjection
import org.koin.core.module.Module
import org.koin.dsl.bind

fun Module.commonDomainEventDependencies() {
	defaultDependencyInjection()
	single { DomainEventExecutor(get(), get()) }
	single { DomainEventSerializer() }
}

fun Module.domainEventRegistryModule(configuration: BusConfiguration) {
	if (configuration.basePackage != null) {
		single { ReflectionDomainEventRegistry(configuration.basePackage!!) } bind DomainEventRegistry::class
	} else {
		single { NoopDomainEventRegistry() } bind DomainEventRegistry::class
	}
}
