package io.github.magonxesp.bus

import io.github.magonxesp.bus.domain.command.Command
import io.github.magonxesp.bus.domain.command.CommandClass
import io.github.magonxesp.bus.domain.command.CommandHandlerClass
import io.github.magonxesp.bus.domain.event.DomainEvent
import io.github.magonxesp.bus.domain.event.DomainEventClass
import io.github.magonxesp.bus.domain.event.DomainEventSubscriberClass
import io.kotest.matchers.shouldBe

@set:Synchronized
var commandConsumed = mutableMapOf<CommandClass, CommandHandlerClass>()

@set:Synchronized
var domainEventConsumed = mutableMapOf<DomainEventClass, Set<DomainEventSubscriberClass>>()

fun Command<*>.markAsConsumed(handler: CommandHandlerClass) = synchronized(commandConsumed) {
	commandConsumed[this::class] = handler
}

infix fun Command<*>.shouldBeConsumedBy(handler: CommandHandlerClass) = synchronized(commandConsumed) {
	val consumer = commandConsumed[this::class]
	consumer shouldBe handler
}

fun DomainEvent<*>.markAsConsumed(handler: DomainEventSubscriberClass) = synchronized(commandConsumed) {
	val handlers = domainEventConsumed[this::class]?.toMutableSet() ?: mutableSetOf()
	handlers.add(handler)
	domainEventConsumed[this::class] = handlers
}

infix fun DomainEvent<*>.shouldBeConsumedBy(handler: DomainEventSubscriberClass) = synchronized(commandConsumed) {
	val consumers = domainEventConsumed[this::class] ?: setOf()
	val consumer = consumers.firstOrNull { it == handler }
	consumer shouldBe handler
}
