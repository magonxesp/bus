package io.github.magonxesp.bus.infrastructure.event.rabbitmq

import com.rabbitmq.client.*
import io.github.magonxesp.bus.domain.event.DomainEventConsumer
import io.github.magonxesp.bus.domain.event.DomainEventRegistry
import io.github.magonxesp.bus.domain.event.DomainEventSubscriberClass
import io.github.magonxesp.bus.infrastructure.event.DomainEventExecutor
import io.github.magonxesp.bus.infrastructure.event.DomainEventSerializer
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import org.slf4j.LoggerFactory
import kotlin.concurrent.thread

class RabbitMqPollingDomainEventConsumer(
	private val connection: Connection,
	private val domainEventSerializer: DomainEventSerializer,
	private val domainEventQueueResolver: RabbitMqDomainEventQueueResolver,
	private val domainEventRegistry: DomainEventRegistry,
	private val domainEventExecutor: DomainEventExecutor
) : DomainEventConsumer {
	private val logger = LoggerFactory.getLogger(this::class.java)
	private val consumers = mutableListOf<Job>()

	private fun setupGracefulHook() {
		Runtime.getRuntime().addShutdownHook(thread(start = false) {
			logger.info("Stopping all domain event consumers...")
			consumers.forEach {
				it.cancel()
			}
		})
	}

	private fun processDelivery(channel: Channel, subscriber: DomainEventSubscriberClass, delivery: GetResponse) {
		val deliveryTag = delivery.envelope.deliveryTag
		val domainEventJson = delivery.body.decodeToString()
		logger.debug("Attempting to consume incoming domain event: $domainEventJson")

		val event = domainEventSerializer.deserialize(domainEventJson)

		domainEventExecutor.execute(event, subscriber).onSuccess {
			channel.basicAck(deliveryTag, false)
		}.onFailure {
			channel.basicReject(deliveryTag, false)
		}
	}

	private fun pullDomainEventForSubscriber(channel: Channel, subscriber: DomainEventSubscriberClass) = flow {
		val queue = domainEventQueueResolver.domainEventQueueName(subscriber)
		logger.debug("Pulling next 10 messages from queue: $queue")

		for (i in 0..10) {
			val delivery = channel.basicGet(queue, false) ?: return@flow
			emit(delivery)
		}
	}

	private suspend fun startPolling(channel: Channel, subscriber: DomainEventSubscriberClass) = coroutineScope {
		while (isActive) {
			pullDomainEventForSubscriber(channel, subscriber).collect {
				processDelivery(channel, subscriber, it)
			}

			logger.debug("Waiting for pull next messages...")
			delay(1000)
		}
	}

	private fun startPollingForAllHandlers() = runBlocking {
		val channel = connection.createChannel()
		val registry = domainEventRegistry.domainEventSubscribers()

		for ((_, subscribers) in registry) {
			for (subscriber in subscribers) {
				launch(Dispatchers.IO) {
					startPolling(channel, subscriber)
				}.also {
					consumers.add(it)
				}
			}
		}
	}

	override fun startConsume(block: Boolean) {
		setupGracefulHook()

		if (block) {
			startPollingForAllHandlers()
		} else {
			thread { startPollingForAllHandlers() }
		}
	}
}
