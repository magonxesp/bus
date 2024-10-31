package io.github.magonxesp.bus.infrastructure.command.koin

import io.github.magonxesp.bus.domain.command.CommandBus
import io.github.magonxesp.bus.infrastructure.command.inmemory.InMemoryAsyncCommandBus
import io.github.magonxesp.bus.infrastructure.command.inmemory.InMemorySyncCommandBus
import io.github.magonxesp.bus.infrastructure.shared.koin.BusConfiguration
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

class InMemoryCommandBusConfiguration : BusConfiguration() {
	var async: Boolean = false
	var asyncMaxQueueItems: Int = 100
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

fun inMemoryCommandBusModule(configure: InMemoryCommandBusConfiguration.() -> Unit): Module = module {
	val configuration = InMemoryCommandBusConfiguration().apply { configure() }

	commonCommandDependencies()
	commandRegistryModule(configuration)
	commandBusImplementation(configuration)
}

private fun Module.commandBusImplementation(configuration: InMemoryCommandBusConfiguration) {
	if (configuration.async) {
		single { InMemoryAsyncCommandBus(get(), configuration.asyncMaxQueueItems) } bind CommandBus::class
	} else {
		single { InMemorySyncCommandBus(get()) } bind CommandBus::class
	}
}
