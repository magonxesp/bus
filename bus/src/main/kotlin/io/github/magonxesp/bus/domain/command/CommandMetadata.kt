package io.github.magonxesp.bus.domain.command

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
open class CommandMetadata {
	val createdAt: Instant = Clock.System.now()
	var dispatchedOn: Instant? = null
		internal set
}
