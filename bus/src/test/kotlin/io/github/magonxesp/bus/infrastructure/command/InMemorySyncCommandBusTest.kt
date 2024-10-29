package io.github.magonxesp.bus.infrastructure.command

import io.github.magonxesp.bus.IntegrationTestCase
import io.github.magonxesp.bus.domain.command.UserCreateCommand
import io.github.magonxesp.bus.domain.command.UserCreateCommandHandler
import io.github.magonxesp.bus.domain.command.UserCreateRequest
import io.github.magonxesp.bus.domain.command.randomUserCreateCommand
import io.github.magonxesp.bus.infrastructure.command.inmemory.InMemorySyncCommandBus
import io.github.magonxesp.bus.infrastructure.shared.dependencyinjection.TestDependencyInjectionContainer
import io.github.magonxesp.bus.random
import io.kotest.matchers.shouldBe

class InMemorySyncCommandBusTest : IntegrationTestCase() {
	private val bus = InMemorySyncCommandBus(commandRegistry, TestDependencyInjectionContainer())

	init {
	    test("it should dispatch and consume the command") {
			val command = randomUserCreateCommand()

			bus.dispatch(command)

			command.isConsumedBy(UserCreateCommandHandler::class) shouldBe true
		}
	}
}
