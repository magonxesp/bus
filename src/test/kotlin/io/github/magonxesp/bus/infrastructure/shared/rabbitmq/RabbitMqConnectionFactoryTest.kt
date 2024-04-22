package io.github.magonxesp.bus.infrastructure.shared.rabbitmq

import io.github.magonxesp.bus.RabbitMqIntegrationTestCase

class RabbitMqConnectionFactoryTest : RabbitMqIntegrationTestCase() {
	init {
		test("it should connect to rabbitmq") {
			val configuration = RabbitMqConnectionConfiguration(
				username = rabbitmq.adminUsername,
				password = rabbitmq.adminPassword,
				port = rabbitmq.amqpPort
			)

			val factory = RabbitMqConnectionFactory(configuration)
			val connection = factory.getConnection()

			connection.createChannel()
			connection.close()
		}

		xtest("it should connect to rabbitmq with custom vhost") {
			val configuration = RabbitMqConnectionConfiguration(
				username = rabbitmq.adminUsername,
				password = rabbitmq.adminPassword,
				virtualHost = "test_vhost",
				port = rabbitmq.amqpPort
			)

			val factory = RabbitMqConnectionFactory(configuration)
			val connection = factory.getConnection()

			connection.createChannel()
			connection.close()
		}
	}
}
