package io.github.magonxesp.example.plugins

import io.github.magonxesp.example.repository.*
import io.github.magonxesp.example.service.BookOfferService
import io.github.magonxesp.example.service.OrderService
import io.github.magonxesp.example.service.handlers.UserSaveCommandHandler
import io.github.magonxesp.example.service.listeners.UpdateBookStockOnBookOfferCreated
import org.jetbrains.exposed.sql.Database
import org.koin.core.KoinApplication
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {
	single { connectDatabase() } bind Database::class
}

val repositoryModule = module {
	single { UserRepository(get()) }
	single { BookRepository(get()) }
	single { BookOfferRepository(get()) }
	single { OrderItemsRepository(get()) }
	single { OrderRepository(get()) }
}

val servicesModule = module {
	single { BookOfferService(get(), get()) }
	single { OrderService(get(), get()) }
}

val handlersModule = module {
	single { UserSaveCommandHandler(get()) }
}

val listenersModule = module {
	single { UpdateBookStockOnBookOfferCreated(get()) }
}

fun KoinApplication.addAppModules() {
	modules(
		databaseModule,
		repositoryModule,
		servicesModule,
		handlersModule,
		listenersModule,
	)
}
