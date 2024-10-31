package io.github.magonxesp.bus.infrastructure.event.rabbitmq

import com.rabbitmq.client.*
import io.github.magonxesp.bus.domain.event.DomainEventConsumer
import io.github.magonxesp.bus.domain.event.DomainEventRegistry
import io.github.magonxesp.bus.domain.event.DomainEventSubscriberClass
import io.github.magonxesp.bus.infrastructure.event.DomainEventExecutor
import io.github.magonxesp.bus.infrastructure.event.DomainEventSerializer
import org.slf4j.LoggerFactory
import kotlin.concurrent.thread

class RabbitMqDomainEventConsumer(
	private val connection: Connection,
	private val domainEventSerializer: DomainEventSerializer,
	private val domainEventQueueResolver: RabbitMqDomainEventQueueResolver,
	private val domainEventRegistry: DomainEventRegistry,
	private val domainEventExecutor: DomainEventExecutor
) : DomainEventConsumer {
	private val logger = LoggerFactory.getLogger(this::class.java)
	private val consumers = mutableListOf<String>()

	@get:Synchronized
	@set:Synchronized
	private var isRunning = false

	@get:Synchronized
	@set:Synchronized
	private var isConsuming = false

	private fun createConsumer(channel: Channel, subscriber: DomainEventSubscriberClass) = object : DefaultConsumer(channel) {
		override fun handleDelivery(consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties?, body: ByteArray?) {
			val deliveryTag = envelope?.deliveryTag ?: return
			val domainEventJson = body?.decodeToString() ?: return
			logger.debug("Attempting to consume incoming domain event: $domainEventJson")

			val event = domainEventSerializer.deserialize(domainEventJson)
			isConsuming = true

			domainEventExecutor.execute(event, subscriber).onSuccess {
				channel.basicAck(deliveryTag, false)
				isConsuming = false
			}.onFailure {
				channel.basicReject(deliveryTag, false)
				isConsuming = false
			}
		}
	}

	private fun blockThread() {
		isRunning = true
		while (isRunning) {
			Thread.sleep(100)
		}
	}

	private fun setupGracefulHook(channel: Channel) {
		Runtime.getRuntime().addShutdownHook(thread(start = false) {
			isRunning = false
			consumers.forEach { consumer -> channel.basicCancel(consumer) }

			while (isConsuming) {
				logger.info("Consumers are finishing their work, waiting for they finish...")
				Thread.sleep(100)
			}
		})
	}

	override fun startConsume(block: Boolean) {
		val channel = connection.createChannel()
		val registry = domainEventRegistry.domainEventSubscribers()

		for ((_, subscribers) in registry) {
			for (subscriber in subscribers) {
				val queue = domainEventQueueResolver.domainEventQueueName(subscriber)
				val consumerHandler = createConsumer(channel, subscriber)
				val consumer = channel.basicConsume(queue, false, consumerHandler)
				consumers.add(consumer)
			}
		}

		setupGracefulHook(channel)
		if (block) blockThread()
	}
}
