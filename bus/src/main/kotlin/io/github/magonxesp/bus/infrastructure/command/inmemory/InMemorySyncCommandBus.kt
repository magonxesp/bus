package io.github.magonxesp.bus.infrastructure.command.inmemory

import io.github.magonxesp.bus.domain.command.Command
import io.github.magonxesp.bus.domain.command.CommandBus
import io.github.magonxesp.bus.domain.command.CommandHandler
import io.github.magonxesp.bus.domain.command.CommandRegistry
import io.github.magonxesp.bus.infrastructure.command.CommandExecutor
import io.github.magonxesp.bus.infrastructure.shared.dependencyinjection.BusDependencyInjectionHelper

class InMemorySyncCommandBus(
	private val commandExecutor: CommandExecutor
) : CommandBus {
	override fun dispatch(command: Command<*>) {
		commandExecutor.execute(command)
	}
}
