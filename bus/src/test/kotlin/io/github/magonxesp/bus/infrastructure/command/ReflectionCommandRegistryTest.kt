package io.github.magonxesp.bus.infrastructure.command

import io.github.magonxesp.bus.IntegrationTestCase
import io.github.magonxesp.bus.domain.command.CommandHandlers
import io.github.magonxesp.bus.domain.command.TaskListCreateCommand
import io.github.magonxesp.bus.domain.command.TaskListCreateCommandHandler
import io.github.magonxesp.bus.domain.command.UserCreateCommand
import io.github.magonxesp.bus.domain.command.UserCreateCommandHandler
import io.github.magonxesp.bus.infrastructure.command.registry.ReflectionCommandRegistry
import io.kotest.matchers.maps.shouldContainAll

class ReflectionCommandRegistryTest : IntegrationTestCase() {
	init {
	    test("it should found the declared command handlers") {
			val registry = ReflectionCommandRegistry("io.github.magonxesp.bus")
			val expected: CommandHandlers = mapOf(
				UserCreateCommand::class to UserCreateCommandHandler::class,
				TaskListCreateCommand::class to TaskListCreateCommandHandler::class,
			)

			val handlers = registry.commandHandlers()

			handlers shouldContainAll expected
		}
	}
}
