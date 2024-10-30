package io.github.magonxesp.bus

import io.github.magonxesp.bus.infrastructure.command.koin.rabbitMqCommandBusModule
import io.github.magonxesp.bus.infrastructure.command.rabbitmq.RabbitMqCommandQueueAutoDeclaration
import io.kotest.core.spec.Spec
import org.koin.core.context.startKoin
import org.slf4j.LoggerFactory
import org.testcontainers.containers.RabbitMQContainer
import org.testcontainers.utility.DockerImageName

abstract class RabbitMqIntegrationTestCase : IntegrationTestCase() {
	private val logger = LoggerFactory.getLogger(this::class.java)

	companion object {
		val rabbitmq = RabbitMQContainer(DockerImageName.parse("rabbitmq:3.13.1-management-alpine"))
		var koinStarted = false
	}

	private fun startRabbitMq() {
		rabbitmq.start()
		logger.debug("RabbitMQ started!")
		logger.debug("RabbitMQ user: ${rabbitmq.adminUsername}")
		logger.debug("RabbitMQ password: ${rabbitmq.adminPassword}")
		logger.debug("RabbitMQ port (AMQP): ${rabbitmq.amqpPort}")
		logger.debug("RabbitMQ port (management): ${rabbitmq.httpPort}")
	}

	private fun setupRabbitMqBus() {
		if (koinStarted) return

		val koin = startKoin {
			rabbitMqCommandBusModule {
				username = rabbitmq.adminUsername
				password = rabbitmq.adminPassword
				port = rabbitmq.amqpPort
				basePackage = "io.github.magonxesp.bus"
			}
		}

		koin.koin.get<RabbitMqCommandQueueAutoDeclaration>().apply { declareAllQueues() }

		koinStarted = true
	}

	override suspend fun beforeSpec(spec: Spec) {
		super.beforeSpec(spec)
		startRabbitMq()
		setupRabbitMqBus()
	}
}
