package io.github.magonxesp.bus.domain.command

import io.github.magonxesp.bus.random
import kotlinx.serialization.Serializable

@Serializable
data class UserCreateRequest(
	val username: String,
	val email: String,
	val roles: Set<String>,
)

@Serializable
data class UserCreateCommand(
	override val data: UserCreateRequest
) : Command<UserCreateRequest>()

fun randomUserCreateCommand() = UserCreateCommand(
	data = UserCreateRequest(
		username = random().random.randomString(30),
		email = random().internet.email(),
		roles = setOf("GUEST", "VISITOR")
	)
)
