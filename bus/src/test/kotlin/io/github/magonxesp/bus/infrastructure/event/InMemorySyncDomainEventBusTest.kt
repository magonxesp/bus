package io.github.magonxesp.bus.infrastructure.event

import io.github.magonxesp.bus.InMemoryIntegrationTestCase
import io.github.magonxesp.bus.domain.event.CountTotalUsersOnUserCreated
import io.github.magonxesp.bus.domain.event.SendWelcomeEmailOnUserCreated
import io.github.magonxesp.bus.domain.event.UserCreated
import io.github.magonxesp.bus.infrastructure.event.inmemory.InMemorySyncDomainEventBus
import io.github.magonxesp.bus.shouldBeConsumedBy
import org.koin.java.KoinJavaComponent.inject
import java.util.*

class InMemorySyncDomainEventBusTest : InMemoryIntegrationTestCase() {
	private val eventBus by inject<InMemorySyncDomainEventBus>(InMemorySyncDomainEventBus::class.java)

	init {
	    test("it should publish and handle domain events") {
			val event = UserCreated(
				id = UUID.randomUUID().toString(),
				name = "test",
				email = "example_email@example.com",
				avatarUrl = "https://avatar.example.com/avatar.png"
			)

			eventBus.publish(event)

			event shouldBeConsumedBy CountTotalUsersOnUserCreated::class
			event shouldBeConsumedBy SendWelcomeEmailOnUserCreated::class
		}
	}
}
