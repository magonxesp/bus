package io.github.magonxesp.bus

import io.github.magonxesp.bus.infrastructure.event.ReflectionDomainEventRegistry
import io.github.magonxesp.bus.infrastructure.event.rabbitmq.RabbitMqDomainEventQueueSetup
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConnectionConfiguration
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConnectionFactory
import io.kotest.core.spec.Spec
import org.slf4j.LoggerFactory
import org.testcontainers.containers.RabbitMQContainer
import org.testcontainers.utility.DockerImageName

abstract class RabbitMqIntegrationTestCase : IntegrationTestCase() {
	private val logger = LoggerFactory.getLogger(this::class.java)

	companion object {
		val rabbitmq = RabbitMQContainer(DockerImageName.parse("rabbitmq:3.13.1-management-alpine"))
	}

	private fun RabbitMQContainer.startAndLog() {
		rabbitmq.start()
		logger.debug("RabbitMQ started!")
		logger.debug("RabbitMQ user: ${rabbitmq.adminUsername}")
		logger.debug("RabbitMQ password: ${rabbitmq.adminPassword}")
		logger.debug("RabbitMQ port (AMQP): ${rabbitmq.amqpPort}")
		logger.debug("RabbitMQ port (management): ${rabbitmq.httpPort}")
	}

	protected val connectionFactory: RabbitMqConnectionFactory
		get() = RabbitMqConnectionFactory(
			RabbitMqConnectionConfiguration(
				username = rabbitmq.adminUsername,
				password = rabbitmq.adminPassword,
				port = rabbitmq.amqpPort
			)
		)

	protected val domainEventRegistry = ReflectionDomainEventRegistry("io.github.magonxesp.bus")

	private val queueSetup: RabbitMqDomainEventQueueSetup
		get() = RabbitMqDomainEventQueueSetup(connectionFactory, domainEventRegistry)

	override suspend fun beforeSpec(spec: Spec) {
		super.beforeSpec(spec)
		rabbitmq.startAndLog()
		queueSetup.setupQueues()
	}
}
