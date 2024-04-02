package io.github.magonxesp.bus.event.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

abstract class DomainEvent {
	var attributes = mutableMapOf<String, Any?>()
		internal set
	var occurredOn: Instant = Clock.System.now()
		internal set

	abstract val eventName: String

	override fun equals(other: Any?): Boolean {
		return other is DomainEvent
			&& attributes == other.attributes
			&& occurredOn == other.occurredOn
	}
}
