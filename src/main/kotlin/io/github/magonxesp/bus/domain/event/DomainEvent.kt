package io.github.magonxesp.bus.domain.event

import io.github.magonxesp.bus.domain.shared.BusMessage
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.util.*

abstract class DomainEvent : BusMessage() {
	var occurredOn: Instant = Clock.System.now()
		internal set

	val eventId: String = UUID.randomUUID().toString()
	abstract val eventName: String

	override fun equals(other: Any?): Boolean {
		return other is DomainEvent
			&& attributes == other.attributes
			&& occurredOn == other.occurredOn
	}

	override fun hashCode(): Int {
		var result = attributes.hashCode()
		result = 31 * result + occurredOn.hashCode()
		result = 31 * result + eventName.hashCode()
		return result
	}
}
