package com.magonxesp.bus.command.infrastructure

import com.magonxesp.bus.command.domain.CommandHandlers

class CommandRegistry {
	companion object {
		private var registeredCommandHandlers: CommandHandlers = mapOf()
	}

	fun registerCommandHandlers(handlers: CommandHandlers) {
		registeredCommandHandlers = handlers
	}

	val handlers: CommandHandlers
		get() = registeredCommandHandlers
}
