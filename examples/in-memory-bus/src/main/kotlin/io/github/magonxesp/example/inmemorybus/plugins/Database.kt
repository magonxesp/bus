package io.github.magonxesp.example.inmemorybus.plugins

import io.github.magonxesp.example.inmemorybus.model.BooksTable
import io.github.magonxesp.example.inmemorybus.model.UsersTable
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.java.KoinJavaComponent.get

fun connectDatabase() = Database.connect(
	url = "jdbc:postgresql://localhost:5432/example",
	user = "example",
	driver = "org.postgresql.Driver",
	password = "example",
)

fun setupSchema(database: Database) = transaction(database) {
	SchemaUtils.create(BooksTable, UsersTable)
}

fun Application.configureDatabase() {
	setupSchema(get(Database::class.java))
}
