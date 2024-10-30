package io.github.magonxesp.bus.infrastructure.command.koin

import io.github.magonxesp.bus.domain.command.CommandRegistry
import io.github.magonxesp.bus.infrastructure.command.CommandExecutor
import io.github.magonxesp.bus.infrastructure.command.registry.NoopCommandRegistry
import io.github.magonxesp.bus.infrastructure.command.registry.ReflectionCommandRegistry
import io.github.magonxesp.bus.infrastructure.shared.dependencyinjection.BusDependencyInjectionHelper
import io.github.magonxesp.bus.infrastructure.shared.koin.BusConfiguration
import io.github.magonxesp.bus.infrastructure.shared.koin.KoinDependencyInjectionHelper
import org.koin.core.module.Module
import org.koin.dsl.bind

fun Module.commonDependencies() {
	single { KoinDependencyInjectionHelper() } bind BusDependencyInjectionHelper::class
	single { CommandExecutor(get(), get()) }
}

fun Module.commandRegistryModule(configuration: BusConfiguration) {
	single {
		when {
			configuration.basePackage != null -> ReflectionCommandRegistry(configuration.basePackage!!)
			else -> NoopCommandRegistry()
		}
	} bind CommandRegistry::class
}
