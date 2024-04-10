package io.github.magonxesp.bus.infrastructure.event.rabbitmq

import io.github.magonxesp.bus.domain.event.DomainEvent
import io.github.magonxesp.bus.domain.event.DomainEventRegistry
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConfiguration
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConnectionFactory
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class RabbitMqDomainEventQueueSetup(
	private val connectionFactory: RabbitMqConnectionFactory,
	private val domainEventRegistry: DomainEventRegistry,
	configuration: RabbitMqConfiguration = RabbitMqConfiguration()
) : RabbitMqDomainEventClient(configuration) {
	fun setupQueues() {
		val connection = connectionFactory.getConnection()
		val channel = connection.createChannel()
		val domainEventSubscribers = domainEventRegistry.domainEventSubscribers()

		for (entry in domainEventSubscribers) {
			val domainEventClass = entry.key as KClass<DomainEvent>

			for (subscriberClass in entry.value) {
				channel.createQueue(domainEventClass.createInstance(), subscriberClass)
			}
		}
	}
}
