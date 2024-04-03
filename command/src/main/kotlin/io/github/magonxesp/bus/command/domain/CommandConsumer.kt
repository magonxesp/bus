package io.github.magonxesp.bus.command.domain

interface CommandConsumer {
	fun consume(): Result<Unit>
}
