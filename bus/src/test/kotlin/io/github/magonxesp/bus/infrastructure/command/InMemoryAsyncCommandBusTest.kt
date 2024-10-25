package io.github.magonxesp.bus.infrastructure.command

import io.github.magonxesp.bus.IntegrationTestCase
import io.github.magonxesp.bus.domain.command.UserCreateCommand
import io.github.magonxesp.bus.domain.command.UserCreateCommandHandler
import io.github.magonxesp.bus.infrastructure.command.inmemory.InMemoryAsyncCommandBus
import io.github.magonxesp.bus.infrastructure.command.inmemory.InMemorySyncCommandBus
import io.github.magonxesp.bus.infrastructure.shared.dependencyinjection.TestDependencyInjectionContainer
import io.github.magonxesp.bus.random
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay

class InMemoryAsyncCommandBusTest : IntegrationTestCase() {
	private val bus = InMemoryAsyncCommandBus(commandRegistry, TestDependencyInjectionContainer())

	init {
	    test("it should dispatch and consume the command") {
			val command = UserCreateCommand(
				username = random().random.randomString(30),
				email = random().internet.email(),
				roles = setOf("GUEST", "VISITOR")
			)

			bus.dispatch(command)
			delay(1000)

			command.isConsumedBy(UserCreateCommandHandler::class) shouldBe true
		}
	}
}
