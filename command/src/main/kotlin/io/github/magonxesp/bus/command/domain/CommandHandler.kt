package io.github.magonxesp.bus.command.domain

interface CommandHandler<T : Command> {
	fun handle(command: T): Result<Unit>
}
