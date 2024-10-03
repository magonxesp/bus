package io.github.magonxesp.example.model


import io.github.magonxesp.bus.domain.command.Command
import io.github.magonxesp.example.plugins.UUIDSerializer
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import java.util.*

object UsersTable : Table(name = "users") {
	val id = uuid("id")
	val name = varchar("name", length = 50)

	override val primaryKey = PrimaryKey(id)
}

@Serializable
data class User(
	@Serializable(with = UUIDSerializer::class)
	val id: UUID,
	val name: String,
)

fun ResultRow.toUserEntity() = User(
	id = get(UsersTable.id),
	name = get(UsersTable.name),
)

@Serializable
data class UserCommand(
	val id: String,
	val name: String,
) : Command {
	fun toUser() = User(
		id = UUID.fromString(id),
		name = name,
	)
}
