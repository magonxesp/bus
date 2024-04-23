package io.github.magonxesp.bus.infrastructure.command

import io.github.magonxesp.bus.IntegrationTestCase
import io.github.magonxesp.bus.domain.event.command.TaskListCreateCommand
import io.github.magonxesp.bus.domain.event.command.UserCreateCommand
import io.github.magonxesp.bus.random
import io.kotest.matchers.shouldBe
import java.util.*

class CommandSerializerTest : IntegrationTestCase() {
	init {
		test("it should serialize and deserialize command") {
			val command = UserCreateCommand(
				username = random().random.randomString(30),
				email = random().internet.email(),
				roles = setOf("GUEST", "VISITOR")
			)

			val serialized = command.serializeToJson()
			val deserialized = serialized.deserializeCommand(UserCreateCommand::class)

			deserialized shouldBe command
		}

		test("it should serialize and deserialize command with object values") {
			val command = TaskListCreateCommand(
				tasks = (0..50).map {
					TaskListCreateCommand.Task(
						title = random().random.randomString(30),
						status = random().random.randomString(30),
						description = random().random.randomString(255),
						assignee = TaskListCreateCommand.User(id = UUID.randomUUID().toString())
					)
				}
			)

			val serialized = command.serializeToJson()
			val deserialized = serialized.deserializeCommand(TaskListCreateCommand::class)

			deserialized shouldBe command
		}
	}
}
