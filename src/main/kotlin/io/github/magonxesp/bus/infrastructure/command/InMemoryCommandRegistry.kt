package io.github.magonxesp.bus.infrastructure.command

import io.github.magonxesp.bus.domain.command.Command
import io.github.magonxesp.bus.domain.command.CommandHandler
import io.github.magonxesp.bus.domain.command.CommandHandlers
import io.github.magonxesp.bus.domain.command.CommandRegistry
import io.github.magonxesp.bus.domain.shared.getParameter
import kotlin.reflect.KClass
import kotlin.reflect.full.functions
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.jvmErasure

class InMemoryCommandRegistry : CommandRegistry {
	private val registry: MutableMap<KClass<*>, KClass<*>> = mutableMapOf()

	fun addHandler(command: KClass<*>, commandHandler: KClass<*>) {
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
