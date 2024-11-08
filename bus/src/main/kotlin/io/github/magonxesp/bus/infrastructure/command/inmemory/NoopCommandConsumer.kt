package io.github.magonxesp.bus.infrastructure.command.inmemory

import io.github.magonxesp.bus.domain.command.CommandConsumer

class NoopCommandConsumer : CommandConsumer {
	override fun startConsume(block: Boolean) { }
}
