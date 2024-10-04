package io.github.magonxesp.example.model


import io.github.magonxesp.example.plugins.UUIDSerializer
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import java.util.*

object BooksTable : Table(name = "books") {
	val id = uuid("id")
	val title = varchar("title", length = 255)
	val author = varchar("author", length = 255)
	val stock = integer("stock").default(0)
	val price = double("price").default(0.0)
	val offerId = reference("offer_id", BookOffersTable.id).nullable()

	override val primaryKey = PrimaryKey(id)
}

@Serializable
data class Book(
	@Serializable(with = UUIDSerializer::class)
	val id: UUID,
	val title: String,
	val author: String,
	val stock: Int = 0,
	val price: Double = 0.0,
	@Serializable(with = UUIDSerializer::class)
	val offerId: UUID? = null,
)

fun ResultRow.toBookEntity() = Book(
	id = get(BooksTable.id),
	title = get(BooksTable.title),
	author = get(BooksTable.author),
	stock = get(BooksTable.stock),
	price = get(BooksTable.price),
	offerId = get(BooksTable.offerId),
)
