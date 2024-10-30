package io.github.magonxesp.bus.infrastructure.command.inmemory

import io.github.magonxesp.bus.domain.command.Command
import io.github.magonxesp.bus.domain.command.CommandBus
import io.github.magonxesp.bus.infrastructure.command.CommandExecutor
import org.slf4j.LoggerFactory
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.PriorityBlockingQueue
import kotlin.concurrent.thread
import kotlin.reflect.KClass

class InMemoryAsyncCommandBus(
	private val commandExecutor: CommandExecutor,
	private val maxQueueItems: Int = 100
) : CommandBus {
	private val queues = mutableMapOf<KClass<*>, LinkedBlockingQueue<Command<*>>>()
	private var queuesProcessing = mutableSetOf<String>()
	private val logger = LoggerFactory.getLogger(this::class.java)

	private fun startProcessQueueFor(commandType: KClass<*>, queue: LinkedBlockingQueue<Command<*>>) {
		if (queuesProcessing.contains(commandType.qualifiedName!!)) return

		thread {
			logger.info("Waiting for new commands of $commandType")

			while (true) {
				commandExecutor.execute(queue.take())
			}
		}

		queuesProcessing.add(commandType.qualifiedName!!)
	}

	override fun dispatch(command: Command<*>) {
		val queue = queues[command::class] ?: LinkedBlockingQueue<Command<*>>(maxQueueItems).also {
			queues[command::class] = it
		}

		startProcessQueueFor(command::class, queue)
		queue.put(command)
	}
}
