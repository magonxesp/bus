package io.github.magonxesp.bus.infrastructure.event

import io.github.magonxesp.bus.domain.shared.attribute.Attributes
import io.github.magonxesp.bus.domain.event.DomainEvent
import io.github.magonxesp.bus.infrastructure.shared.createSharedMoshiInstance
import kotlinx.datetime.Instant
import kotlin.reflect.full.createInstance

data class SerializableDomainEvent(
	val eventName: String,
	val attributes: Attributes,
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
		_attributes = deserialized.attributes.toMutableMap()
		occurredOn = deserialized.occurredOn
	}
}
