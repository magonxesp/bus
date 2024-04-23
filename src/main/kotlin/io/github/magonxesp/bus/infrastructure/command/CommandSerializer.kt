package io.github.magonxesp.bus.infrastructure.command

import io.github.magonxesp.bus.domain.command.Command
import io.github.magonxesp.bus.infrastructure.shared.createJacksonObjectMapperInstance
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

private val objectMapper = createJacksonObjectMapperInstance()

fun Command.serializeToJson() = objectMapper.writeValueAsString(this)

fun String.deserializeCommand(klass: KClass<*>): Command {
	if (!klass.isSubclassOf(Command::class)) {
		error("$klass is not a subclass of ${Command::class}")
	}

	return objectMapper.readValue(this, klass.java) as Command
}
