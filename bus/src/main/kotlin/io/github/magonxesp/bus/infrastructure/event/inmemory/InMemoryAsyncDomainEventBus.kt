package io.github.magonxesp.bus.infrastructure.event.inmemory

import io.github.magonxesp.bus.domain.event.DomainEvent
import io.github.magonxesp.bus.domain.event.DomainEventBus
import io.github.magonxesp.bus.domain.event.DomainEventRegistry
import io.github.magonxesp.bus.domain.event.DomainEventSubscriberClass
import io.github.magonxesp.bus.infrastructure.event.DomainEventExecutor
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

private typealias BlockingQueue = LinkedBlockingQueue<DomainEvent<*>>

class InMemoryAsyncDomainEventBus(
	private val registry: DomainEventRegistry,
	private val domainEventExecutor: DomainEventExecutor,
	maxThreads: Int = 4
): DomainEventBus {
	private val threadPoolExecutor = ThreadPoolExecutor(1, maxThreads, 30, TimeUnit.SECONDS, LinkedBlockingQueue())

	override fun publish(vararg domainEvent: DomainEvent<*>) {
		for (event in domainEvent) {
			val subscribers = registry.domainEventSubscribers()[event::class] ?: setOf()

			for (subscriber in subscribers) {
				threadPoolExecutor.submit {
					domainEventExecutor.execute(event, subscriber)
				}
			}
		}
	}
}
