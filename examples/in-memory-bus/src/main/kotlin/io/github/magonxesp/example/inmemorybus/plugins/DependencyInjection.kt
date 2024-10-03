package io.github.magonxesp.example.inmemorybus.plugins

import io.github.magonxesp.example.inmemorybus.repository.BookRepository
import io.github.magonxesp.example.inmemorybus.repository.UserRepository
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {
	single { connectDatabase() } bind Database::class
}

val repositoryModule = module {
	single { UserRepository(get()) }
	single { BookRepository(get()) }
}

fun Application.configureDependencyInjection() {
	startKoin {
		modules(
			databaseModule,
			repositoryModule
		)
	}
}
