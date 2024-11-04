package io.github.magonxesp.example.rabbitmq

import io.github.magonxesp.bus.domain.command.CommandConsumer
import io.github.magonxesp.bus.domain.event.DomainEventConsumer
import io.github.magonxesp.example.plugins.configureDatabase
import io.github.magonxesp.example.plugins.configureRouting
import io.github.magonxesp.example.plugins.configureSerialization
import io.github.magonxesp.example.plugins.configureEnvironment
import io.ktor.server.application.*
import org.koin.dsl.koinApplication
import org.koin.java.KoinJavaComponent.inject

fun main(args: Array<String>) {
	if (System.getenv("CONSUMER_MODE") == "true") {
		configureConsumer()
		startConsumer()
	} else {
		io.ktor.server.netty.EngineMain.main(args)
	}
}

fun Application.module() {
	configureEnvironment()
	configureDependencyInjection()
    configureSerialization()
    configureDatabase()
    //configureMonitoring()
	configureRabbitMqQueues()
    configureRouting()
}

fun configureConsumer() {
	configureEnvironment()
	configureDependencyInjection()
	configureDatabase()
	configureRabbitMqQueues()
}

fun startConsumer() {
	val commandConsumer by inject<CommandConsumer>(CommandConsumer::class.java)
	val domainEventConsumer by inject<DomainEventConsumer>(DomainEventConsumer::class.java)

	commandConsumer.startConsume()
	domainEventConsumer.startConsume(true)
}
