package io.github.magonxesp.bus.domain.command

interface CommandConsumer {
	fun startConsume(block: Boolean = false)
}
