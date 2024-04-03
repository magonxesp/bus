package com.magonxesp.bun.command.infrastructure

import io.github.magonxesp.bus.command.domain.Command
import io.github.magonxesp.bus.command.infrastructure.CommandSerializer
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.string.shouldContain
import java.util.UUID

class CommandSerializerTest : AnnotationSpec() {
	class ExampleSimpleCommand(
		val id: String,
		val email: String,
		val username: String
	) : Command

	private val exampleCommandId = UUID.randomUUID().toString()

	private fun commandInstance() = ExampleSimpleCommand(
		id = exampleCommandId,
		email = "example@example.com",
		username = "example_username"
	)

	private val expectedJson = "{\"name\":\"example_simple_command\",\"attributes\":{\"id\":\"$exampleCommandId\",\"email\":\"example@example.com\",\"username\":\"example_username\"}}"

	@Test
	fun `it should serialize a command`() {
		val serializer = CommandSerializer()
		val serialized = serializer.serialize(commandInstance())

		serialized shouldBeEqual expectedJson
	}

}
