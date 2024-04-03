package io.github.magonxesp.bus.command.infrastructure

import io.github.magonxesp.bus.command.domain.Command

data class CommandJson(
	val name: String,
	val attributes: Command
)
