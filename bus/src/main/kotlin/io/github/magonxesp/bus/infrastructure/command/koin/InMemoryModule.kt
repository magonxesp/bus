package io.github.magonxesp.bus.infrastructure.command.koin

import io.github.magonxesp.bus.domain.command.CommandBus
import io.github.magonxesp.bus.domain.command.CommandConsumer
import io.github.magonxesp.bus.infrastructure.command.inmemory.InMemoryAsyncCommandBus
import io.github.magonxesp.bus.infrastructure.command.inmemory.InMemorySyncCommandBus
import io.github.magonxesp.bus.infrastructure.command.inmemory.NoopCommandConsumer
import io.github.magonxesp.bus.infrastructure.shared.koin.BusConfiguration
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

class InMemoryCommandBusConfiguration : BusConfiguration() {
	var async: Boolean = false
	var asyncMaxThreads: Int = 4
}

fun inMemoryCommandBusModule(configure: InMemoryCommandBusConfiguration.() -> Unit): Module = module {
	val configuration = InMemoryCommandBusConfiguration().apply { configure() }

	commonCommandDependencies()
	commandRegistryModule(configuration)
	commandBusImplementation(configuration)
	single { NoopCommandConsumer() } bind CommandConsumer::class
}

private fun Module.commandBusImplementation(configuration: InMemoryCommandBusConfiguration) {
	if (configuration.async) {
		single { InMemoryAsyncCommandBus(get(), configuration.asyncMaxThreads) } bind CommandBus::class
	} else {
		single { InMemorySyncCommandBus(get()) } bind CommandBus::class
	}
}
