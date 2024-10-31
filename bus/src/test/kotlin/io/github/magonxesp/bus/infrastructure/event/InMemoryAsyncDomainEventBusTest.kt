package io.github.magonxesp.bus.infrastructure.event

import io.github.magonxesp.bus.InMemoryIntegrationTestCase
import io.github.magonxesp.bus.domain.event.CountTotalUsersOnUserCreated
import io.github.magonxesp.bus.domain.event.SendWelcomeEmailOnUserCreated
import io.github.magonxesp.bus.domain.event.UserCreated
import io.github.magonxesp.bus.infrastructure.event.inmemory.InMemoryAsyncDomainEventBus
import io.github.magonxesp.bus.shouldBeConsumedBy
import kotlinx.coroutines.delay
import org.koin.java.KoinJavaComponent.inject
import java.util.*

class InMemoryAsyncDomainEventBusTest : InMemoryIntegrationTestCase() {
	private val eventBus by inject<InMemoryAsyncDomainEventBus>(InMemoryAsyncDomainEventBus::class.java)

	init {
	    test("it should publish and handle domain events") {
			val event = UserCreated(
				id = UUID.randomUUID().toString(),
				name = "test",
				email = "example_email@example.com",
				avatarUrl = "https://avatar.example.com/avatar.png"
			)

			eventBus.publish(event)

			delay(3000)
			event shouldBeConsumedBy CountTotalUsersOnUserCreated::class
			event shouldBeConsumedBy SendWelcomeEmailOnUserCreated::class
		}
	}
}
