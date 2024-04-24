package io.github.magonxesp.bus.infrastructure.event

import io.github.magonxesp.bus.RabbitMqIntegrationTestCase
import io.github.magonxesp.bus.domain.event.events.UserCreated
import io.github.magonxesp.bus.domain.event.events.CountTotalUsersOnUserCreated
import io.github.magonxesp.bus.domain.event.events.SendWelcomeEmailOnUserCreated
import io.kotest.matchers.collections.shouldContainAnyOf
import io.kotest.matchers.maps.shouldContainKey

class ReflectionDomainEventRegistryTest : RabbitMqIntegrationTestCase() {
	init {
		test("it should scan for domain events subscribers") {
			val expectedSubscribers = listOf(
				CountTotalUsersOnUserCreated::class,
				SendWelcomeEmailOnUserCreated::class
			)

			val registry = ReflectionDomainEventRegistry("io.github.magonxesp.bus.domain.event")
			val subscribers = registry.domainEventSubscribers()

			subscribers shouldContainKey UserCreated::class
			subscribers[UserCreated::class]!! shouldContainAnyOf expectedSubscribers
		}
	}
}
