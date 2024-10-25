package io.github.magonxesp.bus.domain.command

data class UserCreateCommand(
	val username: String,
	val email: String,
	val roles: Set<String>,
) : Command()
