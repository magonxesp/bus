package io.github.magonxesp.bus.event.infrastructure

import com.squareup.moshi.rawType
import io.github.magonxesp.bus.core.domain.getParameter
import io.github.magonxesp.bus.event.domain.DomainEvent
import io.github.magonxesp.bus.event.domain.DomainEventRegistry
import io.github.magonxesp.bus.event.domain.DomainEventSubscriber
import org.reflections.Reflections
import org.reflections.scanners.Scanners.*
import kotlin.reflect.KClass
import kotlin.reflect.full.functions
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.javaType
import kotlin.reflect.jvm.jvmErasure

class ReflectionDomainEventRegistry(basePackage: String) : DomainEventRegistry {
	private val reflections = Reflections(basePackage)

	override fun domainEventSubscribers(): Map<KClass<*>, Set<KClass<*>>> {
		val registry = mutableMapOf<KClass<*>, MutableSet<KClass<*>>>()
		val subscriberClasses = reflections
			.get(SubTypes.of(DomainEventSubscriber::class.java)
			.asClass<DomainEventSubscriber<*>>())
			.map { it.kotlin }

		for (subscriberClass in subscriberClasses) {
			val domainEventClasses = subscriberClass.functions
				.filter { it.name == "handle" }
				.map { it.getParameter("event").type.jvmErasure }

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
