package io.github.magonxesp.bus.event.infrastructure

import com.magonxesp.bus.shared.domain.getParameter
import com.magonxesp.bus.shared.infrastructure.createSharedMoshiInstance
import io.github.magonxesp.bus.event.domain.DomainEvent
import kotlinx.datetime.Instant
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.primaryConstructor

data class SerializableDomainEvent(
	val eventName: String,
	val attributes: Map<String, Any?>,
	val occurredOn: Instant
)

internal val jsonSerializer = createSharedMoshiInstance()

internal fun DomainEvent.serializeToJson(): String {
	val serializable = SerializableDomainEvent(
		eventName = eventName,
		attributes = attributes,
		occurredOn = occurredOn
	)

	val adapter = jsonSerializer.adapter(SerializableDomainEvent::class.java)
	return adapter.toJson(serializable)
}

internal inline fun <reified T : DomainEvent> String.deserializeDomainEvent(): T? {
	val adapter = jsonSerializer.adapter(SerializableDomainEvent::class.java)
	val deserialized = adapter.fromJson(this) ?: return null

	return T::class.createInstance().apply {
		attributes = deserialized.attributes.toMutableMap()
		occurredOn = deserialized.occurredOn
	}
}
