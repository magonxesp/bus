package io.github.magonxesp.bus.infrastructure.command

import io.github.magonxesp.bus.IntegrationTestCase
import io.github.magonxesp.bus.domain.command.CommandHandlers
import io.github.magonxesp.bus.domain.command.TaskListCreateCommand
import io.github.magonxesp.bus.domain.command.TaskListCreateCommandHandler
import io.github.magonxesp.bus.domain.command.UserCreateCommand
import io.github.magonxesp.bus.domain.command.UserCreateCommandHandler
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.maps.shouldContainAll

class InMemoryCommandRegistryTest : IntegrationTestCase() {
	init {
	    test("it should found the declared command handlers") {
			val registry = InMemoryCommandRegistry()
			val expected: CommandHandlers = mapOf(
				UserCreateCommand::class to UserCreateCommandHandler::class,
				TaskListCreateCommand::class to TaskListCreateCommandHandler::class,
			)

			// set the handlers manually
			registry.addHandler(UserCreateCommand::class, UserCreateCommandHandler::class)
			registry.addHandler(TaskListCreateCommand::class, TaskListCreateCommandHandler::class)

			val handlers = registry.commandHandlers()

			handlers shouldContainAll expected
		}

		test("it should not allow non command classes") {
			val registry = InMemoryCommandRegistry()

			shouldThrow<RuntimeException> {
				registry.addHandler(String::class, UserCreateCommandHandler::class)
			}
		}

		class NonCommandHandlerClass

		test("it should not allow non command handler classes") {
			val registry = InMemoryCommandRegistry()

			shouldThrow<RuntimeException> {
				registry.addHandler(UserCreateCommand::class, NonCommandHandlerClass::class)
			}
		}

		test("it should not allow add a command handler that not handles the desired command") {
			val registry = InMemoryCommandRegistry()

			shouldThrow<RuntimeException> {
				registry.addHandler(UserCreateCommand::class, TaskListCreateCommandHandler::class)
			}
		}
	}
}
