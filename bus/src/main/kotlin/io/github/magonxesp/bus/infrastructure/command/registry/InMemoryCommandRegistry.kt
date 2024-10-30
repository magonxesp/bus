package io.github.magonxesp.bus.infrastructure.command.registry

import io.github.magonxesp.bus.domain.command.*
import io.github.magonxesp.bus.domain.shared.getParameter
import kotlin.reflect.KClass
import kotlin.reflect.full.functions
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.jvmErasure

class InMemoryCommandRegistry : CommandRegistry {
	var registry: MutableMap<CommandClass, CommandHandlerClass> = mutableMapOf()
		internal set

	fun addHandler(command: CommandClass, commandHandler: CommandHandlerClass) {
		if (!command.isSubclassOf(Command::class)) {
			throw RuntimeException("The $command must be a subclass of ${Command::class}")
		}

		if (!commandHandler.isSubclassOf(CommandHandler::class)) {
			throw RuntimeException("The $commandHandler must be a subclass of ${CommandHandler::class}")
		}

		val commandClass = commandHandler.functions
			.filter { it.name == "handle" }
			.map { it.getParameter("command").type.jvmErasure }
			.first()

		if (command != commandClass) {
			throw RuntimeException("The handle method of $commandHandler must have the $command as type parameter")
		}

		registry[command] = commandHandler
	}

	override fun commandHandlers(): CommandHandlers = registry
}
