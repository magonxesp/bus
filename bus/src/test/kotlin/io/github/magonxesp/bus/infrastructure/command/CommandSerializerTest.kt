package io.github.magonxesp.bus.infrastructure.command

import io.github.magonxesp.bus.IntegrationTestCase
import io.github.magonxesp.bus.domain.command.TaskListCreateCommand
import io.github.magonxesp.bus.domain.command.TaskListCreateRequest
import io.github.magonxesp.bus.domain.command.UserCreateCommand
import io.github.magonxesp.bus.domain.command.UserCreateRequest
import io.github.magonxesp.bus.random
import io.kotest.matchers.shouldBe
import java.util.*

class CommandSerializerTest : IntegrationTestCase() {
	private val commandSerializer = CommandSerializer()

	init {
		test("it should serialize and deserialize command") {
			val request = UserCreateRequest(
				username = random().random.randomString(30),
				email = random().internet.email(),
				roles = setOf("GUEST", "VISITOR")
			)

			val command = UserCreateCommand(data = request)
			val serialized = commandSerializer.serialize(command)
			val deserialized = commandSerializer.deserialize(serialized)

			deserialized shouldBe command
		}

		test("it should serialize and deserialize command with object values") {
			val request = TaskListCreateRequest(
				tasks = (0..50).map {
					TaskListCreateRequest.Task(
						title = random().random.randomString(30),
						status = random().random.randomString(30),
						description = random().random.randomString(255),
						assignee = TaskListCreateRequest.User(id = UUID.randomUUID().toString())
					)
				}
			)

			val command = TaskListCreateCommand(data = request)
			val serialized = commandSerializer.serialize(command)
			val deserialized = commandSerializer.deserialize(serialized)

			deserialized shouldBe command
		}
	}
}
