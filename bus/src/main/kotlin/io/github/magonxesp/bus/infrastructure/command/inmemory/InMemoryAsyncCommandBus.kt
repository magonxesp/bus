package io.github.magonxesp.bus.infrastructure.command.inmemory

import io.github.magonxesp.bus.domain.command.Command
import io.github.magonxesp.bus.domain.command.CommandBus
import io.github.magonxesp.bus.domain.command.CommandHandler
import io.github.magonxesp.bus.domain.command.CommandRegistry
import io.github.magonxesp.bus.infrastructure.command.serializeToJson
import io.github.magonxesp.bus.infrastructure.shared.dependencyinjection.BusDependencyInjectionHelper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
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
		private val logger = LoggerFactory.getLogger(this::class.java)

		private fun processQueue(registry: CommandRegistry, dependencyInjectionHelper: BusDependencyInjectionHelper) {
			if (isQueueProcessing) return

			thread {
				logger.info("Waiting for new commands")
				while (true) {
					val command = queue.take()

					try {
						logger.debug("Incoming command {}: {}", command::class, command.serializeToJson())
						val commandHandlers = registry.commandHandlers()
						val handlerClass = commandHandlers[command::class] ?: throw RuntimeException("Command handler for ${command::class} not found")
						val handlerInstance = dependencyInjectionHelper.get<CommandHandler<Command>>(handlerClass)
						handlerInstance.handle(command)
						logger.debug("Command {} handled successfully by {}", command::class, handlerClass)
					} catch (exception: Exception) {
						logger.error("Command {} failed with exception", command::class, exception)
					}
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
