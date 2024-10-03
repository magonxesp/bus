package io.github.magonxesp.example.repository

import io.github.magonxesp.example.model.Order
import io.github.magonxesp.example.model.OrdersTable
import io.github.magonxesp.example.model.toOrderEntity
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.*

class OrderRepository(private val database: Database) {
	fun findByUser(userId: UUID) = transaction(database) {
		OrdersTable.selectAll()
			.where { OrdersTable.userId eq userId }
			.map { it.toOrderEntity() }
	}

	fun findById(id: UUID) = transaction(database) {
		OrdersTable.selectAll().where { OrdersTable.id eq id }
			.singleOrNull()?.toOrderEntity()
	}

	fun save(order: Order): Unit = transaction(database) {
		if (findById(order.id) != null) {
			OrdersTable.update(where = { OrdersTable.id eq order.id }) {
				it[id] = order.id
				it[userId] = order.userId
			}
		} else {
			OrdersTable.insert {
				it[id] = order.id
				it[userId] = order.userId
			}
		}
	}
}
