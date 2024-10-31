package io.github.magonxesp.bus.infrastructure.event

import io.github.magonxesp.bus.RabbitMqIntegrationTestCase
import io.github.magonxesp.bus.infrastructure.event.rabbitmq.RabbitMqDomainEventQueueAutoDeclaration
import org.koin.java.KoinJavaComponent.inject

class RabbitMqDomainEventQueueAutoDeclarationTest : RabbitMqIntegrationTestCase() {
	init {
		test("it should create the domain event subscribers queues") {
			val queueAutoDeclaration by inject<RabbitMqDomainEventQueueAutoDeclaration>(RabbitMqDomainEventQueueAutoDeclaration::class.java)

			queueAutoDeclaration.declareAllQueues()
		}
	}
}
