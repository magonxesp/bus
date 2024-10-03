package io.github.magonxesp.example.model

import io.github.magonxesp.example.plugins.UUIDSerializer
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import java.util.UUID

object OrderItemsTable : Table(name = "order_items") {
	val id = uuid("id")
	val bookId = reference("book_id", BooksTable.id)
	val quantity = integer("quantity")
	val totalPrice = double("total_price")
	val offerId = reference("offer_id", BookOffersTable.id)
	val orderId = reference("order_id", OrdersTable.id)

	override val primaryKey = PrimaryKey(id)
}

@Serializable
data class OrderItem(
	@Serializable(with = UUIDSerializer::class)
	val id: UUID,
	@Serializable(with = UUIDSerializer::class)
	val bookId: UUID,
	val quantity: Int,
	val totalPrice: Double,
	@Serializable(with = UUIDSerializer::class)
	val offerId: UUID,
	@Serializable(with = UUIDSerializer::class)
	val orderId: UUID,
)

fun ResultRow.toOrderItemEntity() = OrderItem(
	id = get(OrderItemsTable.id),
	bookId = get(OrderItemsTable.bookId),
	quantity = get(OrderItemsTable.quantity),
	totalPrice = get(OrderItemsTable.totalPrice),
	offerId = get(OrderItemsTable.offerId),
	orderId = get(OrderItemsTable.orderId),
)
