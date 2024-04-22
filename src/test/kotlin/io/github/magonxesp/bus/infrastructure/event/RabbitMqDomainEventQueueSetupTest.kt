package io.github.magonxesp.bus.infrastructure.event

import io.github.magonxesp.bus.RabbitMqIntegrationTestCase
import io.github.magonxesp.bus.infrastructure.event.rabbitmq.RabbitMqDomainEventQueueSetup
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConfiguration
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConnectionConfiguration
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConnectionFactory

class RabbitMqDomainEventQueueSetupTest : RabbitMqIntegrationTestCase() {
	init {
		test("it should create the domain event subscribers queues") {
			val configuration = RabbitMqConfiguration()
			val registry = ReflectionDomainEventRegistry("io.github.magonxesp.bus")
			val setup = RabbitMqDomainEventQueueSetup(connectionFactory, registry, configuration)

			setup.setupQueues()
		}
	}
}
