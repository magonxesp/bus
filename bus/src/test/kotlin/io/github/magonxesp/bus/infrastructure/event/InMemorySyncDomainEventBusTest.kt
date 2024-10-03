package io.github.magonxesp.bus.infrastructure.event

import io.github.magonxesp.bus.IntegrationTestCase
import io.github.magonxesp.bus.domain.event.CountTotalUsersOnUserCreated
import io.github.magonxesp.bus.domain.event.SendWelcomeEmailOnUserCreated
import io.github.magonxesp.bus.domain.event.UserCreated
import io.github.magonxesp.bus.infrastructure.event.inmemory.InMemorySyncDomainEventBus
import io.github.magonxesp.bus.infrastructure.shared.dependencyinjection.TestDependencyInjectionContainer
import io.kotest.matchers.shouldBe
import java.util.*

class InMemorySyncDomainEventBusTest : IntegrationTestCase() {
	private val eventBus = InMemorySyncDomainEventBus(domainEventRegistry, TestDependencyInjectionContainer())

	init {
	    test("it should publish and handle domain events") {
			val event = UserCreated(
				id = UUID.randomUUID().toString(),
				name = "test",
				email = "example_email@example.com",
				avatarUrl = "https://avatar.example.com/avatar.png"
			)

			eventBus.publish(event)

			event.isConsumedBy(CountTotalUsersOnUserCreated::class) shouldBe true
			event.isConsumedBy(SendWelcomeEmailOnUserCreated::class) shouldBe true
		}
	}
}
