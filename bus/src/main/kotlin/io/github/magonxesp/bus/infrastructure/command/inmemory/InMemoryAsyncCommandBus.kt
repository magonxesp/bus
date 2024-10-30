package io.github.magonxesp.bus.infrastructure.command.inmemory

import io.github.magonxesp.bus.domain.command.Command
import io.github.magonxesp.bus.domain.command.CommandBus
import io.github.magonxesp.bus.infrastructure.command.CommandExecutor
import org.slf4j.LoggerFactory
import java.util.concurrent.PriorityBlockingQueue
import kotlin.concurrent.thread
import kotlin.reflect.KClass

class InMemoryAsyncCommandBus(
	private val commandExecutor: CommandExecutor
) : CommandBus {
	private val queues = mutableMapOf<KClass<*>, PriorityBlockingQueue<Command<*>>>()
	private var queuesProcessing = mutableSetOf<String>()
	private val logger = LoggerFactory.getLogger(this::class.java)

	private fun startProcessQueueFor(commandType: KClass<*>, queue: PriorityBlockingQueue<Command<*>>) {
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
		val queue = queues[command::class] ?: PriorityBlockingQueue<Command<*>>().also { queues[command::class] = it }
		startProcessQueueFor(command::class, queue)
		queue.add(command)
	}
}
