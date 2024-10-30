package io.github.magonxesp.bus.domain.command

interface CommandRegistry {
	/**
	 * Get the command handlers
	 */
	fun commandHandlers(): Map<CommandClass, CommandHandlerClass>
}
