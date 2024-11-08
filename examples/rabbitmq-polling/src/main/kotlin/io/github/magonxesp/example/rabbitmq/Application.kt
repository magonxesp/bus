package io.github.magonxesp.example.rabbitmq

import io.github.magonxesp.bus.domain.command.CommandConsumer
import io.github.magonxesp.bus.domain.event.DomainEventConsumer
import io.github.magonxesp.example.plugins.configureDatabase
import io.github.magonxesp.example.plugins.configureEnvironment
import io.github.magonxesp.example.plugins.configureRouting
import io.github.magonxesp.example.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.java.KoinJavaComponent.inject
import org.slf4j.LoggerFactory

fun main(args: Array<String>) {
	if (System.getenv("CONSUMER_MODE") == "true") {
		configureConsumer()
		startConsumer()
	} else {
		embeddedServer(Netty, 8080, module = module).start(wait = true)
	}
}

val module: Application.() -> Unit = {
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
	val logger = LoggerFactory.getLogger("Application Consumer")
	val commandConsumer by inject<CommandConsumer>(CommandConsumer::class.java)
	val domainEventConsumer by inject<DomainEventConsumer>(DomainEventConsumer::class.java)

	logger.info("Starting RabbitMQ consumers")

	commandConsumer.startConsume()
	domainEventConsumer.startConsume(true)
}
