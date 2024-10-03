package io.github.magonxesp.example.plugins

import io.github.magonxesp.bus.infrastructure.command.koin.inMemoryCommandBusModule
import io.github.magonxesp.example.repository.BookRepository
import io.github.magonxesp.example.repository.UserRepository
import io.github.magonxesp.example.service.UserSaveCommandHandler
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

val handlers = module {
	single { UserSaveCommandHandler(get()) }
}

fun Application.configureDependencyInjection() {
	startKoin {
		modules(
			databaseModule,
			repositoryModule,
			handlers,
			inMemoryCommandBusModule("io.github.magonxesp.example")
		)
	}
}
