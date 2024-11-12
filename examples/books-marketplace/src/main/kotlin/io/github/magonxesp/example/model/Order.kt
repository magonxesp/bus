package io.github.magonxesp.example.model

import io.github.magonxesp.bus.domain.command.Command
import io.github.magonxesp.bus.domain.event.DomainEvent
import io.github.magonxesp.example.plugins.UUIDSerializer
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import java.util.UUID

object OrdersTable : Table(name = "orders") {
	val id = uuid("id")
	val userId = uuid("user_id")
	val total = double("total")

	override val primaryKey = PrimaryKey(id)
}

@Serializable
data class Order(
	@Serializable(with = UUIDSerializer::class)
	val id: UUID,
	@Serializable(with = UUIDSerializer::class)
	val userId: UUID,
	val total: Double,
)

fun ResultRow.toOrderEntity() = Order(
	id = get(OrdersTable.id),
	userId = get(OrdersTable.userId),
	total = get(OrdersTable.total)
)

@Serializable
data class OrderCreateRequest(
	@Serializable(with = UUIDSerializer::class)
	val id: UUID,
	@Serializable(with = UUIDSerializer::class)
	val userId: UUID,
	val items: List<OrderItemSelectRequest>
)

data class OrderCreateCommand(
	override val data: OrderCreateRequest
) : Command<OrderCreateRequest>()

data class OrderCreatedEvent(
	override val attributes: Attributes
) : DomainEvent<OrderCreatedEvent.Attributes>() {
	constructor(orderId: UUID) : this(Attributes(orderId))

	class Attributes(val orderId: UUID)

	override val eventName = "order_created"
}
