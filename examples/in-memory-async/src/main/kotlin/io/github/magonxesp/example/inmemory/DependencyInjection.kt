package io.github.magonxesp.example.inmemory

import io.github.magonxesp.bus.infrastructure.command.koin.inMemoryCommandBusModule
import io.github.magonxesp.bus.infrastructure.event.koin.inMemoryDomainEventModule
import io.github.magonxesp.example.plugins.addAppModules
import io.ktor.server.application.*
import org.koin.core.context.startKoin

fun Application.configureDependencyInjection() {
	startKoin {
		addAppModules()
		modules(
			inMemoryCommandBusModule {
				basePackage = "io.github.magonxesp.example"
				async = true
			},
			inMemoryDomainEventModule {
				basePackage = "io.github.magonxesp.example"
				async = true
			}
		)
	}
}
