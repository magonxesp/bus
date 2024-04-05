package io.github.magonxesp.bus.infrastructure.shared.rabbitmq

import com.rabbitmq.client.Channel

private fun String.prefixedQueueName(): String {
	if (RabbitMqConfiguration.namespace.isNotBlank()) {
		return RabbitMqConfiguration.namespace + "." + this
	}

	return this
}

fun Channel.createFanoutQueue(exchange: String, queueName: String) {
	val prefixedQueueName = queueName.prefixedQueueName()

	exchangeDeclare(exchange, "fanout", true)
	queueDeclare(prefixedQueueName, true, false, false, null)
	queueBind(prefixedQueueName, exchange, exchange + "_rk")
}

fun Channel.createDirectQueue(exchange: String, routingKey: String, queueName: String) {
	val prefixedQueueName = queueName.prefixedQueueName()

	exchangeDeclare(exchange, "direct", true)
	queueDeclare(prefixedQueueName, true, false, false, null)
	queueBind(prefixedQueueName, exchange, routingKey)
}
