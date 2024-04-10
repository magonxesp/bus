package io.github.magonxesp.bus.infrastructure.event

import io.github.magonxesp.bus.infrastructure.event.rabbitmq.RabbitMqDomainEventQueueSetup
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConfiguration
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConnectionConfiguration
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConnectionFactory
import io.kotest.core.spec.style.FunSpec

class RabbitMqDomainEventQueueSetupTest : FunSpec({

	test("it should create the domain event subscribers queues") {
		val configuration = RabbitMqConfiguration()
		val factory = RabbitMqConnectionFactory(RabbitMqConnectionConfiguration(
			username = "root",
			password = "root"
		))
		val registry = ReflectionDomainEventRegistry("io.github.magonxesp.bus")
		val setup = RabbitMqDomainEventQueueSetup(factory, registry, configuration)

		setup.setupQueues()
	}

})
