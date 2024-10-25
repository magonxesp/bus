package io.github.magonxesp.bus.domain.command

import kotlinx.datetime.Clock

abstract class Command : Comparable<Command> {
	private val createdAt = Clock.System.now().toEpochMilliseconds()

	override fun compareTo(other: Command): Int {
		val createdAtMs = createdAt
		val otherCreatedAtMs = other.createdAt

		return when {
			createdAtMs < otherCreatedAtMs -> 1
			createdAtMs > otherCreatedAtMs -> -1
			else -> 0
		}
	}
}
