package io.github.magonxesp.bus.infrastructure.event.inmemory

import io.github.magonxesp.bus.domain.event.DomainEventConsumer

class NoopDomainEventConsumer : DomainEventConsumer {
	override fun startConsume(block: Boolean) { }
}
