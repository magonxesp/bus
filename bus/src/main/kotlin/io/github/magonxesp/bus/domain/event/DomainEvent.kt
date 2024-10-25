package io.github.magonxesp.bus.domain.event

import io.github.magonxesp.bus.domain.shared.BusMessage
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.util.*

abstract class DomainEvent : BusMessage(), Comparable<Long> {
	open var occurredOn: Instant = Clock.System.now()
		internal set

	open var eventId: String = UUID.randomUUID().toString()
		internal set

	abstract val eventName: String

	override fun compareTo(other: Long): Int = occurredOn.toEpochMilliseconds().let {
		when {
			it < other -> 1
			it > other -> -1
			else -> 0
		}
	}
}
