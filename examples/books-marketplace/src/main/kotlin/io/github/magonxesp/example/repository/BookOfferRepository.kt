package io.github.magonxesp.example.repository

import io.github.magonxesp.example.model.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.*

class BookOfferRepository(private val database: Database) {
	fun findByBook(bookId: UUID) = transaction(database) {
		BookOffersTable.selectAll()
			.where { BookOffersTable.bookId eq bookId }
			.map { it.toBookOfferEntity() }
	}

	fun findById(id: UUID) = transaction(database) {
		BookOffersTable.selectAll().where { BookOffersTable.id eq id }
			.singleOrNull()?.toBookOfferEntity()
	}

	fun save(offer: BookOffer): Unit = transaction(database) {
		if (findById(offer.id) != null) {
			BookOffersTable.update(where = { BookOffersTable.id eq offer.id }) {
				it[id] = offer.id
				it[bookId] = offer.bookId
				it[sellerId] = offer.sellerId
				it[price] = offer.price
				it[stock] = offer.stock
			}
		} else {
			BookOffersTable.insert {
				it[id] = offer.id
				it[bookId] = offer.bookId
				it[sellerId] = offer.sellerId
				it[price] = offer.price
				it[stock] = offer.stock
			}
		}
	}
}
