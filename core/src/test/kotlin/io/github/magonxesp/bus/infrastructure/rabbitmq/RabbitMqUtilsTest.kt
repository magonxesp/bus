package io.github.magonxesp.bus.infrastructure.rabbitmq

import io.github.magonxesp.bus.core.infrastructure.rabbitmq.RabbitMqConnectionConfiguration
import io.github.magonxesp.bus.core.infrastructure.rabbitmq.RabbitMqConnectionFactory
import io.github.magonxesp.bus.core.infrastructure.rabbitmq.createDirectQueue
import io.github.magonxesp.bus.core.infrastructure.rabbitmq.createFanoutQueue
import io.kotest.core.spec.style.FunSpec

class RabbitMqUtilsTest : FunSpec({

	val configuration = RabbitMqConnectionConfiguration(
		username = "root",
		password = "root",
		host = "localhost",
	)

	val connectionFactory = RabbitMqConnectionFactory(configuration)

	test("it should create a fanout queue") {
		val connection = connectionFactory.getConnection()
		val channel = connection.createChannel()

		channel.createFanoutQueue("fanout_ex", "example_fanout_queue")
	}

	test("it should create a direct queue") {
		val connection = connectionFactory.getConnection()
		val channel = connection.createChannel()

		channel.createDirectQueue("direct_ex", "direct_rk", "example_direct_queue")
	}

})
