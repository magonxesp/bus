package io.github.magonxesp.bus.infrastructure.command

import io.github.magonxesp.bus.domain.command.Command
import io.github.magonxesp.bus.domain.command.CommandRegistry
import io.github.magonxesp.bus.infrastructure.shared.serialization.createJacksonObjectMapperInstance
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

private val objectMapper = createJacksonObjectMapperInstance()

fun Command<*>.serializeToJson() = objectMapper.writeValueAsString(this)

fun String.deserializeCommand(klass: KClass<*>): Command<*> {
	if (!klass.isSubclassOf(Command::class)) {
		error("$klass is not a subclass of ${Command::class}")
	}

	return objectMapper.readValue(this, klass.java) as Command<*>
}

class CommandSerializer(private val commandRegistry: CommandRegistry) {
	private val commandSerializerModule
		get() = SerializersModule {
			polymorphic(Command::class) {
				commandRegistry.commandHandlers().forEach { entry ->
					subclass(entry.key as KClass<Command<Any>>)
				}
			}
		}

	private val json
		get() = Json {
			ignoreUnknownKeys = true
			encodeDefaults = true
			serializersModule = commandSerializerModule
		}

	private val objectMapper = createJacksonObjectMapperInstance()

	fun serialize(command: Command<*>): String {
		return objectMapper.writeValueAsString(command)
	}
	fun deserialize(encoded: String): Command<*> {
		var catchedException: Exception? = null
		val handlers = commandRegistry.commandHandlers().entries

		for (entry in handlers) {
			try {
				val commandClass = entry.key
				return objectMapper.readValue(encoded, commandClass.java) as Command<*>
			} catch (exception: Exception) {
				catchedException = exception
			}
		}

		throw catchedException!!
	}
}
