package io.github.magonxesp.bus.infrastructure.event

import io.github.magonxesp.bus.RabbitMqIntegrationTestCase
import io.github.magonxesp.bus.domain.event.UserCreated
import io.github.magonxesp.bus.domain.event.CountTotalUsersOnUserCreated
import io.github.magonxesp.bus.domain.event.SendWelcomeEmailOnUserCreated
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
