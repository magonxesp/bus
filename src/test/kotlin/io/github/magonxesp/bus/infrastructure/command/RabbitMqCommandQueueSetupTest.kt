package io.github.magonxesp.bus.infrastructure.command

import io.github.magonxesp.bus.RabbitMqIntegrationTestCase
import io.github.magonxesp.bus.infrastructure.command.rabbitmq.RabbitMqCommandQueueSetup

class RabbitMqCommandQueueSetupTest : RabbitMqIntegrationTestCase() {
	init {
	    test("it should create the command handler queues") {
			val registry = ReflectionCommandRegistry("io.github.magonxesp.bus")
			val queueSetup = RabbitMqCommandQueueSetup(connectionFactory, registry)

			queueSetup.setupQueues()
		}
	}
}
