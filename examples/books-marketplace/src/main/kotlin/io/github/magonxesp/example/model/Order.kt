package io.github.magonxesp.example.model

import io.github.magonxesp.example.plugins.UUIDSerializer
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import java.util.UUID

object OrdersTable : Table(name = "orders") {
	val id = uuid("id")
	val userId = reference("user_id", UsersTable.id)

	override val primaryKey = PrimaryKey(id)
}

@Serializable
data class Order(
	@Serializable(with = UUIDSerializer::class)
	val id: UUID,
	@Serializable(with = UUIDSerializer::class)
	val userId: UUID
)

fun ResultRow.toOrderEntity() = Order(
	id = get(OrdersTable.id),
	userId = get(OrdersTable.userId)
)
