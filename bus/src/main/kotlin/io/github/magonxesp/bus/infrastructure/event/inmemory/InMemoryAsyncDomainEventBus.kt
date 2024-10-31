package io.github.magonxesp.bus.infrastructure.event.inmemory

import io.github.magonxesp.bus.domain.event.DomainEvent
import io.github.magonxesp.bus.domain.event.DomainEventBus
import io.github.magonxesp.bus.domain.event.DomainEventRegistry
import io.github.magonxesp.bus.domain.event.DomainEventSubscriberClass
import io.github.magonxesp.bus.infrastructure.event.DomainEventExecutor
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

private typealias BlockingQueue = LinkedBlockingQueue<DomainEvent<*>>

class InMemoryAsyncDomainEventBus(
	private val registry: DomainEventRegistry,
	private val domainEventExecutor: DomainEventExecutor,
	private val maxQueueItems: Int = 100
): DomainEventBus {
	private val queues = mutableMapOf<DomainEventSubscriberClass, BlockingQueue>()
	private var queuesProcessing = mutableSetOf<String>()

	private fun startProcessingQueue(queue: BlockingQueue, subscriber: DomainEventSubscriberClass) {
		if (queuesProcessing.contains(subscriber::class.qualifiedName!!)) return

		thread {
			while (true) {
				val event = queue.take()
				domainEventExecutor.execute(event, subscriber)
			}
		}

		queuesProcessing.add(subscriber::class.qualifiedName!!)
	}

	override fun publish(vararg domainEvent: DomainEvent<*>) {
		for (event in domainEvent) {
			val subscribers = registry.domainEventSubscribers()[event::class] ?: setOf()

			for (subscriber in subscribers) {
				val queue = queues[subscriber] ?: BlockingQueue(maxQueueItems).also {
					queues[subscriber] = it
				}

				startProcessingQueue(queue, subscriber)
				queue.put(event)
			}
		}
	}
}
