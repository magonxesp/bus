package io.github.magonxesp.bus.infrastructure.shared.koin

import com.rabbitmq.client.Connection

class RabbitMqBusConfiguration : BusConfiguration() {
	var username: String = "guest"
	var password: String = "guest"
	var host: String = "localhost"
	var port: Int = 5672
	var virtualHost: String? = null
	var connection: Connection? = null
	var queuePrefix: String = ""
	var consumePolling: Boolean = false
	var useFixedQueueWorker: Boolean = false
	var maxWorkerQueueMessages: Int = 10
		set(value) { if (value < 1) field = 1 else field = value }
	var maxWorkerThreads: Int = 4
		set(value) { if (value < 1) field = 1 else field = value }
}
