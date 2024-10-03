package io.github.magonxesp.bus.infrastructure.event.rabbitmq

import com.rabbitmq.client.Channel
import io.github.magonxesp.bus.domain.event.DomainEvent
import io.github.magonxesp.bus.domain.event.DomainEventBus
import io.github.magonxesp.bus.infrastructure.event.serializeToJson
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConfiguration
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConnectionFactory

class RabbitMqDomainEventBus(
	private val connectionFactory: RabbitMqConnectionFactory,
	configuration: RabbitMqConfiguration = RabbitMqConfiguration()
) : RabbitMqDomainEventClient(configuration), DomainEventBus {
	private fun Channel.publishDomainEvent(domainEvent: DomainEvent) {
		// we publish without routing key because we are publishing to fanout exchanges
		basicPublish(domainEvent.exchangeName, "", null, domainEvent.serializeToJson().toByteArray())
	}

	override fun publish(vararg domainEvent: DomainEvent) {
		val connection = connectionFactory.getConnection()
		val channel = connection.createChannel()

		for (event in domainEvent) {
			channel.publishDomainEvent(event)
		}
	}
}
