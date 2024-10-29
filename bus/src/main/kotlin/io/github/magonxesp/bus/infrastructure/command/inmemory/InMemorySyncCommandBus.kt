package io.github.magonxesp.bus.infrastructure.command.inmemory

import io.github.magonxesp.bus.domain.command.Command
import io.github.magonxesp.bus.domain.command.CommandBus
import io.github.magonxesp.bus.domain.command.CommandHandler
import io.github.magonxesp.bus.domain.command.CommandRegistry
import io.github.magonxesp.bus.infrastructure.shared.dependencyinjection.BusDependencyInjectionHelper

class InMemorySyncCommandBus(
	private val registry: CommandRegistry,
	private val dependencyInjectionHelper: BusDependencyInjectionHelper
) : CommandBus {
	override fun dispatch(command: Command<*>) {
		val commandHandlers = registry.commandHandlers()
		val handlerClass = commandHandlers[command::class] ?: throw RuntimeException("Command handler for ${command::class} not found")
		val handlerInstance = dependencyInjectionHelper.get<CommandHandler<Command<*>>>(handlerClass)
		handlerInstance.handle(command)
	}
}
