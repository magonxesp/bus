package io.github.magonxesp.example.repository

import io.github.magonxesp.example.model.User
import io.github.magonxesp.example.model.UsersTable
import io.github.magonxesp.example.model.toUserEntity
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.*

class UserRepository(private val database: Database) {
	fun findAll() = transaction(database) {
		UsersTable.selectAll()
			.map { it.toUserEntity() }
	}

	fun findById(id: UUID) = transaction(database) {
		UsersTable.selectAll().where { UsersTable.id eq id }
			.singleOrNull()?.toUserEntity()
	}

	fun save(user: User) = transaction(database) {
		if (findById(user.id) != null) {
			UsersTable.update(where = { UsersTable.id eq user.id }) {
				it[id] = user.id
				it[name] = user.name
				it[role] = user.role
			}
		} else {
			UsersTable.insert {
				it[id] = user.id
				it[name] = user.name
				it[role] = user.role
			}
		}
	}
}
