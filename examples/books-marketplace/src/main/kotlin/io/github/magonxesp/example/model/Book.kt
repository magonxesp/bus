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
	val stock = integer("stock")

	override val primaryKey = PrimaryKey(id)
}

@Serializable
data class Book(
	@Serializable(with = UUIDSerializer::class)
	val id: UUID,
	val title: String,
	val author: String,
	val stock: Int = 0,
)

fun ResultRow.toBookEntity() = Book(
	id = get(BooksTable.id),
	title = get(BooksTable.title),
	author = get(BooksTable.author),
	stock = get(BooksTable.stock),
)
