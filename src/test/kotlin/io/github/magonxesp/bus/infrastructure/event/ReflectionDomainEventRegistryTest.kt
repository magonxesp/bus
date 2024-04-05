package io.github.magonxesp.bus.infrastructure.event

import io.github.magonxesp.bus.domain.event.events.UserCreated
import io.github.magonxesp.bus.domain.event.subscribers.CountTotalUsersOnUserCreated
import io.github.magonxesp.bus.domain.event.subscribers.SendWelcomeEmailOnUserCreated
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainAnyOf
import io.kotest.matchers.maps.shouldContainKey

class ReflectionDomainEventRegistryTest : FunSpec({

	test("it should scan for domain events subscribers") {
		val expectedSubscribers = listOf(
			CountTotalUsersOnUserCreated::class,
			SendWelcomeEmailOnUserCreated::class
		)

		val registry = ReflectionDomainEventRegistry("io.github.magonxesp.bus.event.domain")
		val subscribers = registry.domainEventSubscribers()

		subscribers shouldContainKey UserCreated::class
		subscribers[UserCreated::class]!! shouldContainAnyOf expectedSubscribers
	}

})
