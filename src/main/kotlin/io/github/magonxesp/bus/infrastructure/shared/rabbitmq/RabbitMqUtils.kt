package io.github.magonxesp.bus.infrastructure.shared.rabbitmq

import com.rabbitmq.client.Channel

private fun String.prefixedQueueName(): String {
	if (RabbitMqConfiguration.namespace.isNotBlank()) {
		return RabbitMqConfiguration.namespace + "." + this
	}

	return this
}

/**
 * Create the dead letter queue.
 *
 * @return The map with the arguments to send failed messages to this dead letter
 */
private fun Channel.createDirectDeadLetterQueue(
	exchange: String,
	routingKey: String,
	queueName: String
): Map<String, String> {
	val prefixedQueueName = queueName.prefixedQueueName() + ".dlx"
	val deadLetterExchange = "$exchange.dlx"
	val deadLetterRoutingKey = "$routingKey.dlx"

	exchangeDeclare(deadLetterExchange, "direct", true)
	queueDeclare(prefixedQueueName, true, false, false, null)
	queueBind(prefixedQueueName, deadLetterExchange, deadLetterRoutingKey)

	return mapOf(
		"x-dead-letter-exchange" to deadLetterExchange,
		"x-dead-letter-routing-key" to deadLetterRoutingKey
	)
}

fun Channel.createFanoutQueue(exchange: String, queueName: String) {
	val routingKey = "$queueName.fanout".prefixedQueueName()
	val deadLetterArgs = createDirectDeadLetterQueue(exchange, routingKey, queueName)
	val prefixedQueueName = queueName.prefixedQueueName()

	exchangeDeclare(exchange, "fanout", true)
	queueDeclare(prefixedQueueName, true, false, false, deadLetterArgs)
	queueBind(prefixedQueueName, exchange, routingKey)
}

fun Channel.createDirectQueue(exchange: String, routingKey: String, queueName: String) {
	val deadLetterArgs = createDirectDeadLetterQueue(exchange, routingKey, queueName)
	val prefixedQueueName = queueName.prefixedQueueName()

	exchangeDeclare(exchange, "direct", true)
	queueDeclare(prefixedQueueName, true, false, false, deadLetterArgs)
	queueBind(prefixedQueueName, exchange, routingKey)
}
