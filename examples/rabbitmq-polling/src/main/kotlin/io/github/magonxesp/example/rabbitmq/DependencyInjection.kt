package io.github.magonxesp.example.rabbitmq

import io.github.magonxesp.bus.infrastructure.command.koin.rabbitMqCommandBusModule
import io.github.magonxesp.bus.infrastructure.event.koin.rabbitMqDomainEventBusModule
import io.github.magonxesp.example.plugins.*
import io.ktor.server.application.*
import org.koin.core.context.startKoin

fun configureDependencyInjection() {
	startKoin {
		addAppModules()
		modules(
			rabbitMqCommandBusModule {
				basePackage = "io.github.magonxesp.example"
				host = rabbitMqHost
				port = rabbitMqPort
				username = rabbitMqUser
				password = rabbitMqPassword
				consumePolling = true
			},
			rabbitMqDomainEventBusModule {
				basePackage = "io.github.magonxesp.example"
				host = rabbitMqHost
				port = rabbitMqPort
				username = rabbitMqUser
				password = rabbitMqPassword
				consumePolling = true
			}
		)
	}
}
