package io.github.magonxesp.bus.infrastructure.command

import io.github.magonxesp.bus.domain.command.Command
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class DispatchedCommand(
	val data: Command<*>,
	val command: String,
	val dispatchedAt: Instant
) : Comparable<DispatchedCommand> {
	companion object {
		fun fromCommand(command: Command<*>, clock: Clock) = DispatchedCommand(
			data = command,
			command = command::class.qualifiedName!!,
			dispatchedAt = clock.now()
		)
	}

	override fun compareTo(other: DispatchedCommand): Int {
		val dispatchedAtMs = dispatchedAt
		val otherDispatchedAtMs = other.dispatchedAt

		return when {
			dispatchedAtMs < otherDispatchedAtMs -> 1
			dispatchedAtMs > otherDispatchedAtMs -> -1
			else -> 0
		}
	}
}
