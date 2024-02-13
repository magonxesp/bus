package com.magonxesp.bus.command.infrastructure

import com.magonxesp.bus.command.domain.Command
import com.magonxesp.bus.command.domain.CommandHandler

class CommandDispatcher(private val registry: CommandRegistry) {
	//private val logger = LoggerFactory.getLogger(this::class.java)

	fun dispatch(command: Command): Result<Unit> = runCatching {
		/*val handler = registry.handlers[command::class]
			?: return Result.failure(RuntimeException("Failed to handle command ${command::class} because it don't have a handler"))
		val instance = context.getBean(handler.java) as CommandHandler<Command>
		return instance.handle(command).onFailure {
			logger.warn("Command ${command::class} failed: ${it.message}")
		}.onSuccess {
			logger.info("Command ${command::class} consumed")
		}*/
	}
}
