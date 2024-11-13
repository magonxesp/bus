package io.github.magonxesp.bus.infrastructure.event.rabbitmq

import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import io.github.magonxesp.bus.domain.event.DomainEvent
import io.github.magonxesp.bus.domain.event.DomainEventBus
import io.github.magonxesp.bus.infrastructure.event.DomainEventSerializer
import io.github.magonxesp.bus.infrastructure.shared.withTimeSpentLog
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class RabbitMqDomainEventBus(
	private val connection: Connection,
	private val domainEventSerializer: DomainEventSerializer,
	private val domainEventQueueResolver: RabbitMqDomainEventQueueResolver
) : DomainEventBus {
	companion object {
		private var channel: Channel? = null
	}

	@Synchronized
	private fun Connection.getChannel(): Channel {
		if (channel == null || !channel!!.isOpen) {
			channel = createChannel()
		}

		return channel!!
	}

	override fun publish(vararg domainEvent: DomainEvent<*>): Unit = withTimeSpentLog(::publish) {
		val channel = connection.getChannel()

		for (event in domainEvent) {
			val exchange = domainEventQueueResolver.domainEventExchangeName(event::class)
			val serialized = domainEventSerializer.serialize(event)

			channel.basicPublish(exchange, "", null, serialized.toByteArray())
		}
	}
}
