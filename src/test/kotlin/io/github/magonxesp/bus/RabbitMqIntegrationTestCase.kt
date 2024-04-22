package io.github.magonxesp.bus

import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConnectionConfiguration
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConnectionFactory
import io.kotest.core.spec.Spec
import org.testcontainers.containers.RabbitMQContainer
import org.testcontainers.utility.DockerImageName

abstract class RabbitMqIntegrationTestCase : IntegrationTestCase() {
	companion object {
		val rabbitmq = RabbitMQContainer(DockerImageName.parse("rabbitmq:3.13.1-management-alpine"))
	}

	override suspend fun beforeSpec(spec: Spec) {
		rabbitmq.start()
	}

	val connectionFactory: RabbitMqConnectionFactory
		get() = RabbitMqConnectionFactory(
			RabbitMqConnectionConfiguration(
				username = rabbitmq.adminUsername,
				password = rabbitmq.adminPassword,
				port = rabbitmq.amqpPort
			)
		)
}
