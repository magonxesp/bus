package io.github.magonxesp.example.plugins

import io.github.magonxesp.example.model.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.java.KoinJavaComponent.get
import java.io.File

fun connectDatabase() = Database.connect(
	url = "jdbc:postgresql://postgresql:5432/example",
	user = "example",
	driver = "org.postgresql.Driver",
	password = "example",
)

fun setupSchema(database: Database) = transaction(database) {
	SchemaUtils.create(
		BooksTable,
		BookOffersTable,
		UsersTable,
		OrderItemsTable,
		OrdersTable
	)
}

fun Application.configureDatabase() {
	setupSchema(get(Database::class.java))
}