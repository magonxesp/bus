package io.github.magonxesp.bus.command.infrastructure

import io.github.magonxesp.bus.command.domain.CommandHandlers

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
