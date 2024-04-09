package io.github.magonxesp.bus.infrastructure.shared.rabbitmq

import io.kotest.core.spec.style.FunSpec

class RabbitMqConnectionFactoryTest : FunSpec({

	test("it should connect to rabbitmq") {
		val configuration = RabbitMqConnectionConfiguration(
			username = "root",
			password = "root"
		)

		val factory = RabbitMqConnectionFactory(configuration)
		val connection = factory.getConnection()

		connection.createChannel()
		connection.close()
	}

	test("it should connect to rabbitmq with custom vhost") {
		val configuration = RabbitMqConnectionConfiguration(
			username = "root",
			password = "root",
			virtualHost = "example_vhost",
			port = 5673
		)

		val factory = RabbitMqConnectionFactory(configuration)
		val connection = factory.getConnection()

		connection.createChannel()
		connection.close()
	}

})
