package io.github.magonxesp.bus.infrastructure.command.inmemory

import io.github.magonxesp.bus.domain.command.Command
import io.github.magonxesp.bus.domain.command.CommandBus
import io.github.magonxesp.bus.domain.command.CommandHandler
import io.github.magonxesp.bus.domain.command.CommandRegistry
import io.github.magonxesp.bus.infrastructure.shared.dependencyinjection.BusDependencyInjectionHelper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.PriorityQueue
import java.util.concurrent.PriorityBlockingQueue
import kotlin.concurrent.thread

class InMemoryAsyncCommandBus(
	registry: CommandRegistry,
	dependencyInjectionHelper: BusDependencyInjectionHelper
) : CommandBus {
	companion object {
		private val queue = PriorityBlockingQueue<Command>()
		private var isQueueProcessing = false

		private fun processQueue(registry: CommandRegistry, dependencyInjectionHelper: BusDependencyInjectionHelper) {
			if (isQueueProcessing) return

			thread {
				while (true) {
					val command = queue.take()
					val commandHandlers = registry.commandHandlers()
					val handlerClass = commandHandlers[command::class] ?: throw RuntimeException("Command handler for ${command::class} not found")
					val handlerInstance = dependencyInjectionHelper.get<CommandHandler<Command>>(handlerClass)
					handlerInstance.handle(command)
				}
			}

			isQueueProcessing = true
		}
	}

	init {
		processQueue(registry, dependencyInjectionHelper)
	}

	override fun dispatch(command: Command) {
		queue.add(command)
	}
}
