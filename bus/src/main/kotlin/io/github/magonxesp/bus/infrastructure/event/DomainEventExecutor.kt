package io.github.magonxesp.bus.infrastructure.event

import io.github.magonxesp.bus.domain.event.DomainEvent
import io.github.magonxesp.bus.domain.event.DomainEventRegistry
import io.github.magonxesp.bus.domain.event.DomainEventSubscriber
import io.github.magonxesp.bus.domain.event.DomainEventSubscriberClass
import io.github.magonxesp.bus.infrastructure.shared.dependencyinjection.BusDependencyInjectionHelper
import org.slf4j.LoggerFactory

class DomainEventExecutor(
	private val registry: DomainEventRegistry,
	private val dependencyInjectionHelper: BusDependencyInjectionHelper
) {
	private val logger = LoggerFactory.getLogger(this::class.java)

	fun execute(event: DomainEvent<*>): Map<DomainEventSubscriberClass, Result<Unit>> {
		val results = mutableMapOf<DomainEventSubscriberClass, Result<Unit>>()
		val domainEventSubscribers = registry.domainEventSubscribers()
		val subscribers = domainEventSubscribers[event::class] ?: setOf()

		for (subscriber in subscribers) {
			results[subscriber] = execute(event, subscriber)
		}

		return results
	}

	fun execute(event: DomainEvent<*>, subscriber: DomainEventSubscriberClass): Result<Unit> {
		try {
			val subscriberInstance = dependencyInjectionHelper.get<DomainEventSubscriber<DomainEvent<*>>>(subscriber)
			subscriberInstance.handle(event)
			logger.debug("Domain event {} handled successfully by subscriber {}", event::class, subscriber)
			return Result.success(Unit)
		} catch (exception: Exception) {
			logger.warn("Domain event {} handled by subscriber {} failed with exception", event::class, subscriber, exception)
			return Result.failure(exception)
		}
	}
}
