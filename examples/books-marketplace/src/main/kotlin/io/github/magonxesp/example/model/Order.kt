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
	val userId = reference("user_id", UsersTable.id)
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
data class OrderCreateCommand(
	@Serializable(with = UUIDSerializer::class)
	val id: UUID,
	@Serializable(with = UUIDSerializer::class)
	val userId: UUID,
	val items: List<OrderItemSelectCommand>
) : Command()

data class OrderCreatedEvent(
	val orderId: UUID
) : DomainEvent() {
	override val eventName = "order_created"
}
