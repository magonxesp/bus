package io.github.magonxesp.bus.infrastructure.command.inmemory

import io.github.magonxesp.bus.domain.command.Command
import io.github.magonxesp.bus.domain.command.CommandBus
import io.github.magonxesp.bus.domain.command.CommandHandler
import io.github.magonxesp.bus.domain.command.CommandRegistry
import io.github.magonxesp.bus.infrastructure.command.serializeToJson
import io.github.magonxesp.bus.infrastructure.shared.dependencyinjection.BusDependencyInjectionHelper
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import java.util.concurrent.PriorityBlockingQueue
import kotlin.concurrent.thread
import kotlin.reflect.KClass

class InMemoryAsyncCommandBus(
	private val registry: CommandRegistry,
	private val dependencyInjectionHelper: BusDependencyInjectionHelper
) : CommandBus {
	private val queues = mutableMapOf<KClass<*>, PriorityBlockingQueue<Command>>()
	private var queuesProcessing = mutableSetOf<String>()
	private val logger = LoggerFactory.getLogger(this::class.java)

	private fun startProcessQueueFor(commandType: KClass<*>, queue: PriorityBlockingQueue<Command>) {
		if (queuesProcessing.contains(commandType.qualifiedName!!)) return

		thread {
			logger.info("Waiting for new commands of $commandType")

			while (true) {
				val command = queue.take()
				handleCommand(command)
			}
		}

		queuesProcessing.add(commandType.qualifiedName!!)
	}

	private fun handleCommand(command: Command) {
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

	override fun dispatch(command: Command) {
		val queue = queues[command::class] ?: PriorityBlockingQueue<Command>().also { queues[command::class] = it }
		startProcessQueueFor(command::class, queue)
		queue.add(command)
	}
}
