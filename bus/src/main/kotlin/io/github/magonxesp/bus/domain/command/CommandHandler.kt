package io.github.magonxesp.bus.domain.command

interface CommandHandler<T : Command<*>> {
	fun handle(command: T)
}
