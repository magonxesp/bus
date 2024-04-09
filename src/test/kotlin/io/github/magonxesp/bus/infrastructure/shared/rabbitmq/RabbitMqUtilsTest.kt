package io.github.magonxesp.bus.infrastructure.shared.rabbitmq

import io.kotest.core.spec.style.FunSpec
import java.util.*

class RabbitMqUtilsTest : FunSpec({
	val configuration = RabbitMqConnectionConfiguration(
		username = "root",
		password = "root"
	)

	val factory = RabbitMqConnectionFactory(configuration)
	val connection = factory.getConnection()
	val channel = connection.createChannel()

	fun randomExchange() = "test_exchange_" + UUID.randomUUID().toString()
	fun randomRoutingKey() = "test_routing_key_" + UUID.randomUUID().toString()
	fun randomQueueName() = "test_queue_" + UUID.randomUUID().toString()

	test("it should create a queue with direct exchange") {
		val exchange = randomExchange()
		val routingKey = randomRoutingKey()
		val queueName = randomQueueName()

		channel.createDirectQueue(exchange, routingKey, queueName)
	}

	test("it should create a queue with fanout exchange") {
		val exchange = randomExchange()
		val queueName = randomQueueName()

		channel.createFanoutQueue(exchange, queueName)
	}

	test("it should create a queue with direct exchange prefixed") {
		val exchange = randomExchange()
		val routingKey = randomRoutingKey()
		val queueName = randomQueueName()

		RabbitMqConfiguration.namespace = "my_namespace"

		channel.createDirectQueue(exchange, routingKey, queueName)
	}

	test("it should create a queue with fanout exchange prefixed") {
		val exchange = randomExchange()
		val queueName = randomQueueName()

		RabbitMqConfiguration.namespace = "my_namespace"

		channel.createFanoutQueue(exchange, queueName)
	}
})
