package io.github.magonxesp.bus.infrastructure.event

import io.github.magonxesp.bus.RabbitMqIntegrationTestCase
import io.github.magonxesp.bus.domain.event.UserCreated
import io.github.magonxesp.bus.infrastructure.event.rabbitmq.RabbitMqDomainEventBus
import io.kotest.core.spec.Spec
import java.util.*

class RabbitMqDomainEventBusTest : RabbitMqIntegrationTestCase() {
	private lateinit var eventBus: RabbitMqDomainEventBus

	override suspend fun beforeSpec(spec: Spec) {
		super.beforeSpec(spec)
		eventBus = RabbitMqDomainEventBus(connectionFactory)
	}

	init {
		test("it should publish a domain event") {
			val event = UserCreated(
				id = UUID.randomUUID().toString(),
				name = "test",
				email = "example_email@example.com",
				avatarUrl = "https://avatar.example.com/avatar.png"
			)

			eventBus.publish(event)
		}
	}
}
