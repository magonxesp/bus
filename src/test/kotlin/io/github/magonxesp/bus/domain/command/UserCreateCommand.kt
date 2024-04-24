package io.github.magonxesp.bus.domain.command

import io.github.magonxesp.bus.domain.command.Command

data class UserCreateCommand(
	val username: String,
	val email: String,
	val roles: Set<String>,
) : Command
