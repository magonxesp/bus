package io.github.magonxesp.bus.infrastructure.event

import io.github.magonxesp.bus.RabbitMqIntegrationTestCase
import io.github.magonxesp.bus.domain.event.CountTotalUsersOnUserCreated
import io.github.magonxesp.bus.domain.event.SendWelcomeEmailOnUserCreated
import io.github.magonxesp.bus.domain.event.UserCreated
import io.github.magonxesp.bus.infrastructure.event.rabbitmq.RabbitMqDomainEventBus
import io.github.magonxesp.bus.infrastructure.event.rabbitmq.RabbitMqDomainEventConsumer
import io.github.magonxesp.bus.shouldBeConsumedBy
import kotlinx.coroutines.delay
import org.koin.java.KoinJavaComponent.inject
import java.util.*

class RabbitMqDomainEventConsumerTest : RabbitMqIntegrationTestCase() {
	private val eventBus by inject<RabbitMqDomainEventBus>(RabbitMqDomainEventBus::class.java)
	private val eventConsumer by inject<RabbitMqDomainEventConsumer>(RabbitMqDomainEventConsumer::class.java)

	init {
		test("it should consume incoming domain event") {
			val event = UserCreated(
				id = UUID.randomUUID().toString(),
				name = "test",
				email = "example_email@example.com",
				avatarUrl = "https://avatar.example.com/avatar.png"
			)

			eventBus.publish(event)
			eventConsumer.startConsume()

			delay(3000)
			event shouldBeConsumedBy CountTotalUsersOnUserCreated::class
			event shouldBeConsumedBy SendWelcomeEmailOnUserCreated::class
		}
	}
}
