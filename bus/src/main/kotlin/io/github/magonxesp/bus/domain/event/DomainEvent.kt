package io.github.magonxesp.bus.domain.event

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.util.*

abstract class DomainEvent<T> {
	open val occurredOn: Instant = Clock.System.now()
	open val eventId: String = UUID.randomUUID().toString()
	abstract val eventName: String
	abstract val attributes: T
}
