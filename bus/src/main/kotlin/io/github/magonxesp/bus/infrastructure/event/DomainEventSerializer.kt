package io.github.magonxesp.bus.infrastructure.event

import io.github.magonxesp.bus.domain.shared.attribute.Attributes
import io.github.magonxesp.bus.domain.event.DomainEvent
import io.github.magonxesp.bus.infrastructure.shared.createJacksonObjectMapperInstance
import kotlinx.datetime.Instant
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.isSubclassOf

data class SerializableDomainEvent(
	val eventId: String,
	val eventName: String,
	val attributes: Attributes,
	val occurredOn: String
)

internal val jsonSerializer = createJacksonObjectMapperInstance()

internal fun DomainEvent.serializeToJson(): String {
	val serializable = SerializableDomainEvent(
		eventId = eventId,
		eventName = eventName,
		occurredOn = occurredOn.toString(),
		attributes = attributes
	)

	return jsonSerializer.writeValueAsString(serializable)
}

internal inline fun <reified T : DomainEvent> String.deserializeDomainEvent(): T {
	val deserialized = jsonSerializer.readValue(this, SerializableDomainEvent::class.java)

	return T::class.createInstance().apply {
		_attributes = deserialized.attributes.toMutableMap()
		occurredOn = Instant.parse(deserialized.occurredOn)
		eventId = deserialized.eventId
	}
}

/**
 * Deserialize the domain event by the domain event class
 */
internal fun String.deserializeDomainEvent(klass: KClass<*>): DomainEvent {
	val deserialized = jsonSerializer.readValue(this, SerializableDomainEvent::class.java)

	if (!klass.isSubclassOf(DomainEvent::class)) {
		error("$klass is not a subclass of ${DomainEvent::class}")
	}

	return (klass.createInstance() as DomainEvent).apply {
		_attributes = deserialized.attributes.toMutableMap()
		occurredOn = Instant.parse(deserialized.occurredOn)
		eventId = deserialized.eventId
	}
}
