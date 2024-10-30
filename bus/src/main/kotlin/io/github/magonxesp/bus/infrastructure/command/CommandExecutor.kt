package io.github.magonxesp.bus.infrastructure.command

import io.github.magonxesp.bus.domain.command.Command
import io.github.magonxesp.bus.domain.command.CommandHandler
import io.github.magonxesp.bus.domain.command.CommandRegistry
import io.github.magonxesp.bus.infrastructure.shared.dependencyinjection.BusDependencyInjectionHelper
import org.slf4j.LoggerFactory

class CommandExecutor(
	private val registry: CommandRegistry,
	private val dependencyInjectionHelper: BusDependencyInjectionHelper
) {
	private val logger = LoggerFactory.getLogger(this::class.java)

	fun execute(command: Command<*>): Result<Unit> {
		try {
			val commandHandlers = registry.commandHandlers()
			val handlerClass = commandHandlers[command::class] ?: throw RuntimeException("Command handler for ${command::class} not found")
			val handlerInstance = dependencyInjectionHelper.get<CommandHandler<Command<*>>>(handlerClass)

			handlerInstance.handle(command)
			logger.debug("Command {} handled successfully by {}", command::class, handlerClass)

			return Result.success(Unit)
		} catch (exception: Exception) {
			logger.warn("Command {} failed with exception", command::class, exception)
			return Result.failure(exception)
		}
	}
}
