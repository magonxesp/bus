package io.github.magonxesp.bus.domain.event

interface DomainEventConsumer {
	fun startConsume(block: Boolean = true)
}
