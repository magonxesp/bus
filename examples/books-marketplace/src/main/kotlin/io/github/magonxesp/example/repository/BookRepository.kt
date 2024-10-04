package io.github.magonxesp.example.repository

import io.github.magonxesp.example.model.Book
import io.github.magonxesp.example.model.BooksTable
import io.github.magonxesp.example.model.toBookEntity
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.*

class BookRepository(private val database: Database) {
	fun findAll(page: Int = 1, pageSize: Int = 50) = transaction(database) {
		BooksTable.selectAll()
			.limit(pageSize, ((page - 1) * pageSize).toLong())
			.map { it.toBookEntity() }
	}

	fun findById(id: UUID) = transaction(database) {
		BooksTable.selectAll().where { BooksTable.id eq id }
			.singleOrNull()?.toBookEntity()
	}

	fun save(book: Book): Unit = transaction(database) {
		if (findById(book.id) != null) {
			BooksTable.update(where = { BooksTable.id eq book.id }) {
				it[id] = book.id
				it[title] = book.title
				it[author] = book.author
				it[stock] = book.stock
				it[price] = book.price
				it[offerId] = book.offerId
			}
		} else {
			BooksTable.insert {
				it[id] = book.id
				it[title] = book.title
				it[author] = book.author
				it[stock] = book.stock
				it[price] = book.price
				it[offerId] = book.offerId
			}
		}
	}
}
