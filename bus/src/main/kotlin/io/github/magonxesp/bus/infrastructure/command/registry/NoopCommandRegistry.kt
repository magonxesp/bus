package io.github.magonxesp.bus.infrastructure.command.registry

import io.github.magonxesp.bus.domain.command.CommandHandlers
import io.github.magonxesp.bus.domain.command.CommandRegistry

class NoopCommandRegistry : CommandRegistry {
	override fun commandHandlers(): CommandHandlers = mapOf()
}
