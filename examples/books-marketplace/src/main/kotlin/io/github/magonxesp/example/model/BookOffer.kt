package io.github.magonxesp.example.model


import io.github.magonxesp.bus.domain.event.DomainEvent
import io.github.magonxesp.example.plugins.UUIDSerializer
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import java.util.*

object BookOffersTable : Table(name = "book_offers") {
	val id = uuid("id")
	val bookId = reference("book_id", BooksTable.id)
	val sellerId = reference("seller_id", UsersTable.id)
	val price = double("price")
	val stock = integer("stock")

	override val primaryKey = PrimaryKey(id)
}

@Serializable
data class BookOffer(
	@Serializable(with = UUIDSerializer::class)
	val id: UUID,
	@Serializable(with = UUIDSerializer::class)
	val bookId: UUID,
	@Serializable(with = UUIDSerializer::class)
	val sellerId: UUID,
	val price: Double,
	val stock: Int,
)

fun ResultRow.toBookOfferEntity() = BookOffer(
	id = get(BookOffersTable.id),
	bookId = get(BookOffersTable.bookId),
	sellerId = get(BookOffersTable.sellerId),
	price = get(BookOffersTable.price),
	stock = get(BookOffersTable.stock),
)

data class BookOfferCreatedEvent(
	val offerId: UUID,
	val bookId: UUID,
	val stock: Int
) : DomainEvent() {
	override val eventName: String = "book_offer_created"
}