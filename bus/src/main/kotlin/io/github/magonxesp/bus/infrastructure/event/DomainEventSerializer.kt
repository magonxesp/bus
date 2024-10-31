package io.github.magonxesp.bus.infrastructure.event

import io.github.magonxesp.bus.domain.event.DomainEvent
import io.github.magonxesp.bus.infrastructure.shared.serialization.createJacksonObjectMapperInstance
import org.json.JSONObject


class DomainEventSerializer {
	private val objectMapper = createJacksonObjectMapperInstance()

	fun serialize(event: DomainEvent<*>): String {
		val encoded = objectMapper.writeValueAsString(event)
		val jsonObject = JSONObject(encoded)
		jsonObject.put("_type", event::class.qualifiedName!!)
		return jsonObject.toString()
	}

	fun deserialize(encoded: String): DomainEvent<*> {
		val jsonObject = JSONObject(encoded)
		val type = Class.forName(jsonObject.getString("_type"))
		jsonObject.remove("_type")

		return objectMapper.readValue(jsonObject.toString(), type) as DomainEvent<*>
	}
}
