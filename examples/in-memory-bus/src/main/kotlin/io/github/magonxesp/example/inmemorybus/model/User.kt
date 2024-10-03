package io.github.magonxesp.example.inmemorybus.model


import io.github.magonxesp.example.inmemorybus.plugins.UUIDSerializer
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import java.util.*

object UsersTable : Table(name = "users") {
	val id = uuid("id")
	val name = varchar("name", length = 50)
	val role = varchar("role", length = 50)

	override val primaryKey = PrimaryKey(id)
}

@Serializable
data class User(
	@Serializable(with = UUIDSerializer::class)
	val id: UUID,
	val name: String,
	val role: String
)

fun ResultRow.toUserEntity() = User(
	id = get(UsersTable.id),
	name = get(UsersTable.name),
	role = get(UsersTable.role),
)
