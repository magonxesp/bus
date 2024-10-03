package io.github.magonxesp.bus.domain.command

interface CommandBus {
	fun dispatch(command: Command)
}
