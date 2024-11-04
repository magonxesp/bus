package io.github.magonxesp.example.rabbitmq

import io.github.magonxesp.bus.infrastructure.command.rabbitmq.RabbitMqCommandQueueAutoDeclaration
import io.github.magonxesp.bus.infrastructure.event.rabbitmq.RabbitMqDomainEventQueueAutoDeclaration
import io.ktor.server.application.*
import org.koin.java.KoinJavaComponent.inject

fun Application.configureRabbitMqQueues() {
	val commandAutoConfiguration by inject<RabbitMqCommandQueueAutoDeclaration>(RabbitMqCommandQueueAutoDeclaration::class.java)
	val domainEventAutoConfiguration by inject<RabbitMqDomainEventQueueAutoDeclaration>(RabbitMqDomainEventQueueAutoDeclaration::class.java)

	commandAutoConfiguration.declareAllQueues()
	domainEventAutoConfiguration.declareAllQueues()
}
