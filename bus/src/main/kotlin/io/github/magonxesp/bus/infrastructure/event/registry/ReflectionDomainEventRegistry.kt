package io.github.magonxesp.bus.infrastructure.event.registry

import io.github.magonxesp.bus.domain.event.*
import io.github.magonxesp.bus.domain.shared.getParameter
import org.reflections.Reflections
import org.reflections.scanners.Scanners.*
import kotlin.reflect.full.functions
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.jvmErasure

class ReflectionDomainEventRegistry(basePackage: String) : DomainEventRegistry {
	private val registry = mutableMapOf<DomainEventClass, MutableSet<DomainEventSubscriberClass>>()
	private val reflections = Reflections(basePackage)

	override fun domainEventSubscribers(): DomainEventSubscribers {
		if (registry.isNotEmpty()) {
			return registry
		}

		val subscriberClasses = reflections
			.get(SubTypes.of(DomainEventSubscriber::class.java)
			.asClass<DomainEventSubscriber<*>>())
			.map { it.kotlin as DomainEventSubscriberClass }

		for (subscriberClass in subscriberClasses) {
			val domainEventClasses = subscriberClass.functions
				.filter { it.name == "handle" }
				.map { it.getParameter("event").type.jvmErasure as DomainEventClass }

			for (eventClass in domainEventClasses) {
				if (eventClass.isSubclassOf(DomainEvent::class)) {
					val subscribers = registry[eventClass]
					if (subscribers == null) {
						registry[eventClass] = mutableSetOf()
					}

					registry[eventClass]!!.add(subscriberClass)
				}
			}
		}

		return registry
	}
}
