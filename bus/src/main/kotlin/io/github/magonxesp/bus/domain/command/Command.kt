package io.github.magonxesp.bus.domain.command

import kotlinx.datetime.Clock

abstract class Command : Comparable<Long> {
	private val createdAt = Clock.System.now().toEpochMilliseconds()

	override fun compareTo(other: Long): Int = when {
		createdAt < other -> 1
		createdAt > other -> -1
		else -> 0
	}
}
