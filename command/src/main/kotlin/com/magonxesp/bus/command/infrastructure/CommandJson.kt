package com.magonxesp.bus.command.infrastructure

import com.magonxesp.bus.command.domain.Command

data class CommandJson(
	val name: String,
	val attributes: Command
)
