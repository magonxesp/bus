package io.github.magonxesp.bus.infrastructure.command.koin

import io.github.magonxesp.bus.domain.command.CommandBus
import io.github.magonxesp.bus.infrastructure.command.inmemory.InMemoryAsyncCommandBus
import io.github.magonxesp.bus.infrastructure.command.inmemory.InMemorySyncCommandBus
import io.github.magonxesp.bus.infrastructure.shared.koin.BusConfiguration
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

class InMemoryBusConfiguration : BusConfiguration() {
	var async: Boolean = false
}

@Deprecated("use inMemoryBusModule instead")
fun inMemoryCommandBusModule(basePackage: String): Module = inMemoryCommandBusModule {
	this.basePackage = basePackage
}

@Deprecated("use inMemoryBusModule instead")
fun inMemoryAsyncCommandBusModule(basePackage: String): Module = inMemoryCommandBusModule {
	async = true
	this.basePackage = basePackage
}

fun inMemoryCommandBusModule(configure: InMemoryBusConfiguration.() -> Unit): Module = module {
	val configuration = InMemoryBusConfiguration().apply { configure() }

	commonDependencies()
	commandRegistryModule(configuration)
	commandBusImplementation(configuration)
}

private fun Module.commandBusImplementation(configuration: InMemoryBusConfiguration) {
	single {
		when {
			configuration.async -> InMemoryAsyncCommandBus(get())
			else -> InMemorySyncCommandBus(get())
		}
	} bind CommandBus::class
}
