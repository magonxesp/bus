package io.github.magonxesp.bus.command.domain

interface CommandBus {
	fun dispatch(command: Command): Result<Unit>
}
