package io.github.magonxesp.bus.infrastructure.shared.koin

import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import org.koin.core.module.Module
import org.koin.dsl.bind
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

fun Module.rabbitMqConnectionFactory(configuration: RabbitMqBusConfiguration) {
	if (configuration.connection != null) {
		single { configuration.connection!! } bind Connection::class
		return
	}

	fun ConnectionFactory.applyConfig() = apply {
		username = configuration.username
		password = configuration.password
		host = configuration.host
		port = configuration.port
		configuration.virtualHost?.also { virtualHost = it }
	}

	if (configuration.useFixedQueueWorker) {
		single {
			ConnectionFactory().applyConfig().newConnection(
				ThreadPoolExecutor(
					1, configuration.maxWorkerThreads,
					0L, TimeUnit.MILLISECONDS,
					LinkedBlockingQueue(configuration.maxWorkerQueueMessages)
				)
			)
		} bind Connection::class
	} else {
		single { ConnectionFactory().applyConfig().newConnection() } bind Connection::class
	}
}
