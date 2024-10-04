package io.github.magonxesp.example.inmemory

import io.github.magonxesp.bus.infrastructure.command.koin.inMemoryAsyncCommandBusModule
import io.github.magonxesp.bus.infrastructure.command.koin.inMemoryCommandBusModule
import io.github.magonxesp.bus.infrastructure.event.koin.inMemoryAsyncDomainEventBusModule
import io.github.magonxesp.bus.infrastructure.event.koin.inMemoryDomainEventBusModule
import io.github.magonxesp.example.plugins.addAppModules
import io.ktor.server.application.*
import org.koin.core.context.startKoin

fun Application.configureDependencyInjection() {
	startKoin {
		addAppModules()
		modules(
			inMemoryAsyncCommandBusModule("io.github.magonxesp.example"),
			inMemoryAsyncDomainEventBusModule("io.github.magonxesp.example")
		)
	}
}
