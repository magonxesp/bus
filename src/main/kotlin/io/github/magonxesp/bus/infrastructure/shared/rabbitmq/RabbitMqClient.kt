package io.github.magonxesp.bus.infrastructure.shared.rabbitmq

abstract class RabbitMqClient(private val configuration: RabbitMqConfiguration) {
	protected fun String.prefixedQueueName(): String {
		if (configuration.namespace.isNotBlank()) {
			return configuration.namespace + "." + this
		}

		return this
	}
}
