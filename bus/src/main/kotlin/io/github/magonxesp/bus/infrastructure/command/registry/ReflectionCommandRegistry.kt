package io.github.magonxesp.bus.infrastructure.command.registry

import io.github.magonxesp.bus.domain.command.*
import io.github.magonxesp.bus.domain.shared.getParameter
import org.reflections.Reflections
import org.reflections.scanners.Scanners.*
import kotlin.reflect.KClass
import kotlin.reflect.full.functions
import kotlin.reflect.jvm.jvmErasure

class ReflectionCommandRegistry(basePackage: String) : CommandRegistry {
	private val reflections = Reflections(basePackage)

	override fun commandHandlers(): CommandHandlers {
		val registry = mutableMapOf<CommandClass, CommandHandlerClass>()
		val commandHandlers = reflections
			.get(SubTypes.of(CommandHandler::class.java)
				.asClass<CommandHandler<*>>())
			.map { it.kotlin }

		for (handlerClass in commandHandlers) {
			val commandClass = handlerClass.functions
				.filter { it.name == "handle" }
				.map { it.getParameter("command").type.jvmErasure as CommandClass }
				.first()

			registry[commandClass] = handlerClass as CommandHandlerClass
		}

		return registry
	}
}
