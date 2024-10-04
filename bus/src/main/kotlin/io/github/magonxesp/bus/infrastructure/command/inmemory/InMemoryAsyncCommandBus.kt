package io.github.magonxesp.bus.infrastructure.command.inmemory

import io.github.magonxesp.bus.domain.command.Command
import io.github.magonxesp.bus.domain.command.CommandBus
import io.github.magonxesp.bus.domain.command.CommandHandler
import io.github.magonxesp.bus.domain.command.CommandRegistry
import io.github.magonxesp.bus.infrastructure.shared.dependencyinjection.BusDependencyInjectionHelper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

class InMemoryAsyncCommandBus(
	private val registry: CommandRegistry,
	private val dependencyInjectionHelper: BusDependencyInjectionHelper
) : CommandBus {
	private val commands = MutableSharedFlow<Command>()

	init {
		startListenToCommands()
	}

	private fun startListenToCommands() {
		thread {
			runBlocking {
				commands.collect { command ->
					val commandHandlers = registry.commandHandlers()
					val handlerClass = commandHandlers[command::class] ?: throw RuntimeException("Command handler for ${command::class} not found")
					val handlerInstance = dependencyInjectionHelper.get<CommandHandler<Command>>(handlerClass)
					launch { handlerInstance.handle(command) }
				}
			}
		}
	}

	override fun dispatch(command: Command) {
		commands.tryEmit(command)
	}
}
