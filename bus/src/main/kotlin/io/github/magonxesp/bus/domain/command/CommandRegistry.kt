package io.github.magonxesp.bus.domain.command

interface CommandRegistry {
	fun commandHandlers(): Map<CommandClass, CommandHandlerClass>
}
