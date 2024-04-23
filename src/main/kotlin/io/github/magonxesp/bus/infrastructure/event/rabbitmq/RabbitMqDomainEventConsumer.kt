package io.github.magonxesp.bus.infrastructure.event.rabbitmq

import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Channel
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope
import io.github.magonxesp.bus.domain.event.DomainEvent
import io.github.magonxesp.bus.domain.event.DomainEventConsumer
import io.github.magonxesp.bus.domain.event.DomainEventRegistry
import io.github.magonxesp.bus.domain.event.DomainEventSubscriber
import io.github.magonxesp.bus.infrastructure.event.deserializeDomainEvent
import io.github.magonxesp.bus.infrastructure.shared.dependencyinjection.BusDependencyInjectionHelper
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConfiguration
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConnectionFactory
import org.slf4j.LoggerFactory
import kotlin.concurrent.thread
import kotlin.reflect.KClass

class RabbitMqDomainEventConsumer(
	private val registry: DomainEventRegistry,
	private val dependencyInjectionHelper: BusDependencyInjectionHelper,
	private val connectionFactory: RabbitMqConnectionFactory,
	configuration: RabbitMqConfiguration = RabbitMqConfiguration()
) : RabbitMqDomainEventClient(configuration), DomainEventConsumer {
	class SubscriberConsumer(
		channel: Channel,
		private val eventClass: KClass<*>,
		private val subscriber: DomainEventSubscriber<DomainEvent>
	) : DefaultConsumer(channel) {
		private val logger = LoggerFactory.getLogger(this::class.java)

		override fun handleDelivery(consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties?, body: ByteArray?) {
			val deliveryTag = envelope?.deliveryTag ?: return

			try {
				val domainEventJson = body?.decodeToString() ?: return
				val domainEvent = domainEventJson.deserializeDomainEvent(eventClass)
				subscriber.handle(domainEvent)
				channel.basicAck(deliveryTag, false)
			} catch (exception: Exception) {
				logger.warn("${exception::class}: ${exception.message}")
				channel.basicReject(deliveryTag, false)
			}
		}
	}

	override fun startConsume(block: Boolean) {
		val connection = connectionFactory.getConnection()
		val channel = connection.createChannel()
		val domainEventEntries = registry.domainEventSubscribers().entries
		val consumers = mutableListOf<String>()
		var stop = false

		for (domainEventEntry in domainEventEntries) {
			val domainEventClass = domainEventEntry.key

			for (subscriber in domainEventEntry.value) {
				val subscriberInstance = dependencyInjectionHelper.get<DomainEventSubscriber<DomainEvent>>(subscriber)
				val consumer = SubscriberConsumer(channel, domainEventClass, subscriberInstance)
				consumers.add(channel.basicConsume(subscriber.subscriberQueueName, false, consumer))
			}
		}

		connectionFactory.addShutdownTask {
			for (consumer in consumers) {
				channel.basicCancel(consumer)
			}

			channel.close()
			stop = true
		}

		while (block) {
			if (stop) break
		}
	}
}
