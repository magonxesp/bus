package io.github.magonxesp.bus.infrastructure.event

import io.github.magonxesp.bus.InMemoryIntegrationTestCase
import io.github.magonxesp.bus.IntegrationTestCase
import io.github.magonxesp.bus.domain.event.CountTotalUsersOnUserCreated
import io.github.magonxesp.bus.domain.event.SendWelcomeEmailOnUserCreated
import io.github.magonxesp.bus.domain.event.UserCreated
import io.github.magonxesp.bus.infrastructure.event.inmemory.InMemoryAsyncDomainEventBus
import io.kotest.matchers.shouldBe
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
			event.isConsumedBy(CountTotalUsersOnUserCreated::class) shouldBe true
			event.isConsumedBy(SendWelcomeEmailOnUserCreated::class) shouldBe true
		}
	}
}
