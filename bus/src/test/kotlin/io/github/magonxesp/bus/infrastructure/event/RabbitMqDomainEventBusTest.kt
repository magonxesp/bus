package io.github.magonxesp.bus.infrastructure.event

import io.github.magonxesp.bus.RabbitMqIntegrationTestCase
import io.github.magonxesp.bus.domain.event.UserCreated
import io.github.magonxesp.bus.infrastructure.event.rabbitmq.RabbitMqDomainEventBus
import org.koin.java.KoinJavaComponent.inject
import java.util.*

class RabbitMqDomainEventBusTest : RabbitMqIntegrationTestCase() {
	private val bus by inject<RabbitMqDomainEventBus>(RabbitMqDomainEventBus::class.java)

	init {
		test("it should publish a domain event") {
			val event = UserCreated(
				id = UUID.randomUUID().toString(),
				name = "test",
				email = "example_email@example.com",
				avatarUrl = "https://avatar.example.com/avatar.png"
			)

			bus.publish(event)
		}
	}
}
