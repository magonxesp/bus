package io.github.magonxesp.bus.infrastructure.command.koin

import io.github.magonxesp.bus.domain.command.CommandRegistry
import io.github.magonxesp.bus.infrastructure.command.CommandExecutor
import io.github.magonxesp.bus.infrastructure.command.CommandSerializer
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
	single { CommandSerializer() }
}

fun Module.commandRegistryModule(configuration: BusConfiguration) {
	if (configuration.basePackage != null) {
		single { ReflectionCommandRegistry(configuration.basePackage!!) } bind CommandRegistry::class
	} else {
		single { NoopCommandRegistry() } bind CommandRegistry::class
	}
}
