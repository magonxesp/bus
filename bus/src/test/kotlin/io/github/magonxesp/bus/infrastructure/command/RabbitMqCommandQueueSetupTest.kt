package io.github.magonxesp.bus.infrastructure.command

import io.github.magonxesp.bus.RabbitMqIntegrationTestCase
import io.github.magonxesp.bus.infrastructure.command.rabbitmq.RabbitMqCommandQueueAutoDeclaration
import org.koin.java.KoinJavaComponent.inject

class RabbitMqCommandQueueSetupTest : RabbitMqIntegrationTestCase() {
	init {
	    test("it should create the command handler queues") {
			val queueAutoDeclaration by inject<RabbitMqCommandQueueAutoDeclaration>(RabbitMqCommandQueueAutoDeclaration::class.java)

			queueAutoDeclaration.declareAllQueues()
		}
	}
}
