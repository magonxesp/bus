package io.github.magonxesp.bus.infrastructure.shared.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.module.SimpleDeserializers
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.module.SimpleSerializers
import kotlinx.datetime.Instant

class KotlinInstantSerializer : JsonSerializer<Instant?>() {
	override fun serialize(value: Instant?, jgen: JsonGenerator?, provuider: SerializerProvider?) {
		if (value != null) {
			jgen?.writeString(value.toString())
		} else {
			jgen?.writeNull()
		}
	}
}

class KotlinInstantDeserializer : JsonDeserializer<Instant?>() {
	override fun deserialize(parser: JsonParser?, context: DeserializationContext?): Instant? {
		try {
			if (parser == null) return null

			if (parser.currentToken == JsonToken.VALUE_STRING) {
				return Instant.parse(parser.text)
			}
		} catch (_: Exception) {
			// do nothing
		}

		return null
	}
}

val KotlinxInstantModule = object : SimpleModule() {
	override fun setupModule(context: SetupContext?) {
		context?.addSerializers(SimpleSerializers().apply {
			addSerializer(Instant::class.java, KotlinInstantSerializer())
		})

		context?.addDeserializers(SimpleDeserializers().apply {
			addDeserializer(Instant::class.java, KotlinInstantDeserializer())
		})
	}
}
