package io.github.magonxesp.bus.infrastructure.command

import io.github.magonxesp.bus.domain.command.Command
import io.github.magonxesp.bus.domain.command.CommandRegistry
import io.github.magonxesp.bus.infrastructure.shared.serialization.createJacksonObjectMapperInstance
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.json.JSONObject
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

class CommandSerializer {
	private val objectMapper = createJacksonObjectMapperInstance()

	fun serialize(command: Command<*>): String {
		val encoded = objectMapper.writeValueAsString(command)
		val jsonObject = JSONObject(encoded)
		jsonObject.put("_type", command::class.qualifiedName!!)
		return jsonObject.toString()
	}

	fun deserialize(encoded: String): Command<*> {
		val jsonObject = JSONObject(encoded)
		val type = Class.forName(jsonObject.getString("_type"))
		jsonObject.remove("_type")

		return objectMapper.readValue(jsonObject.toString(), type) as Command<*>
	}
}
