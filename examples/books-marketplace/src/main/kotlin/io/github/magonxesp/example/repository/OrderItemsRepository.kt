package io.github.magonxesp.example.repository

import io.github.magonxesp.example.model.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.*

class OrderItemsRepository(private val database: Database) {
	fun findByOrderId(orderId: UUID) = transaction(database) {
		OrderItemsTable.selectAll()
			.where { OrderItemsTable.orderId eq orderId }
			.map { it.toOrderItemEntity() }
	}

	fun findById(id: UUID) = transaction(database) {
		OrderItemsTable.selectAll()
			.where { OrderItemsTable.id eq id }
			.singleOrNull()?.toOrderItemEntity()
	}

	fun save(orderItem: OrderItem): Unit = transaction(database) {
		if (findById(orderItem.id) != null) {
			OrderItemsTable.update(where = { OrderItemsTable.id eq orderItem.id }) {
				it[id] = orderItem.id
				it[bookId] = orderItem.bookId
				it[quantity] = orderItem.quantity
				it[totalPrice] = orderItem.totalPrice
				it[offerId] = orderItem.offerId
				it[orderId] = orderItem.orderId
			}
		} else {
			OrderItemsTable.insert {
				it[id] = orderItem.id
				it[bookId] = orderItem.bookId
				it[quantity] = orderItem.quantity
				it[totalPrice] = orderItem.totalPrice
				it[offerId] = orderItem.offerId
				it[orderId] = orderItem.orderId
			}
		}
	}
}
