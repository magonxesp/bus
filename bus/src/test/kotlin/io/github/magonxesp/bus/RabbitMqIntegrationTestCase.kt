package io.github.magonxesp.bus

import io.github.magonxesp.bus.infrastructure.command.koin.rabbitMqCommandBusModule
import io.github.magonxesp.bus.infrastructure.command.rabbitmq.RabbitMqCommandQueueAutoDeclaration
import io.github.magonxesp.bus.infrastructure.event.koin.rabbitMqDomainEventBusModule
import io.github.magonxesp.bus.infrastructure.event.rabbitmq.RabbitMqDomainEventQueueAutoDeclaration
import io.kotest.core.spec.Spec
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.java.KoinJavaComponent.inject
import org.slf4j.LoggerFactory
import org.testcontainers.containers.RabbitMQContainer
import org.testcontainers.utility.DockerImageName

abstract class RabbitMqIntegrationTestCase : IntegrationTestCase() {
	private val logger = LoggerFactory.getLogger(this::class.java)

	companion object {
		val rabbitmq = RabbitMQContainer(DockerImageName.parse("rabbitmq:3.13.1-management-alpine"))
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
		stopKoin()
		val koinApplication = startKoin {
			modules(
				CommandHandlersModule,
				DomainEventSubscribersModule,
				rabbitMqCommandBusModule {
					username = rabbitmq.adminUsername
					password = rabbitmq.adminPassword
					port = rabbitmq.amqpPort
					basePackage = "io.github.magonxesp.bus"
				},
				rabbitMqDomainEventBusModule {
					username = rabbitmq.adminUsername
					password = rabbitmq.adminPassword
					port = rabbitmq.amqpPort
					basePackage = "io.github.magonxesp.bus"
				}
			)
		}

		koinApplication.koin.get<RabbitMqCommandQueueAutoDeclaration>().apply { declareAllQueues() }
		koinApplication.koin.get<RabbitMqDomainEventQueueAutoDeclaration>().apply { declareAllQueues() }
	}

	override suspend fun beforeSpec(spec: Spec) {
		super.beforeSpec(spec)
		startRabbitMq()
		setupRabbitMqBus()
	}
}
