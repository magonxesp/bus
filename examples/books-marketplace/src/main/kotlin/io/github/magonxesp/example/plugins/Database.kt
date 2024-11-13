package io.github.magonxesp.example.plugins

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.magonxesp.example.model.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.java.KoinJavaComponent.get

fun connectDatabase(): Database {
	val config = HikariConfig().apply {
		jdbcUrl = postgreSqlUrl
		driverClassName = "org.postgresql.Driver"
		username = postgreSqlUser
		password = postgreSqlPassword
		maximumPoolSize = 4
		// as of version 0.46.0, if these options are set here, they do not need to be duplicated in DatabaseConfig
		isReadOnly = false
		transactionIsolation = "TRANSACTION_SERIALIZABLE"
	}

	val dataSource = HikariDataSource(config)
	return Database.connect(datasource = dataSource)
}

fun setupSchema(database: Database) = transaction(database) {
	SchemaUtils.create(
		BooksTable,
		BookOffersTable,
		UsersTable,
		OrderItemsTable,
		OrdersTable
	)
}

fun configureDatabase() {
	setupSchema(get(Database::class.java))
}
