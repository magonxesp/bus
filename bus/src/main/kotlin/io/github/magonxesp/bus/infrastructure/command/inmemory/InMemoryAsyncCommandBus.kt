package io.github.magonxesp.bus.infrastructure.command.inmemory

import io.github.magonxesp.bus.domain.command.Command
import io.github.magonxesp.bus.domain.command.CommandBus
import io.github.magonxesp.bus.infrastructure.command.CommandExecutor
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class InMemoryAsyncCommandBus(
	private val commandExecutor: CommandExecutor,
	maxThreads: Int = 4
) : CommandBus {
	private val threadPoolExecutor = ThreadPoolExecutor(1, maxThreads, 30, TimeUnit.SECONDS, LinkedBlockingQueue())

	override fun dispatch(command: Command<*>) {
		threadPoolExecutor.submit {
			commandExecutor.execute(command)
		}
	}
}
