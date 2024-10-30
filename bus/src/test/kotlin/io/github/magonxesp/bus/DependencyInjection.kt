package io.github.magonxesp.bus

import io.github.magonxesp.bus.domain.command.TaskListCreateCommandHandler
import io.github.magonxesp.bus.domain.command.UserCreateCommandHandler
import io.github.magonxesp.bus.domain.event.CountTotalUsersOnUserCreated
import io.github.magonxesp.bus.domain.event.SendWelcomeEmailOnUserCreated
import org.koin.dsl.module

val CommandHandlersModule = module {
	single { CountTotalUsersOnUserCreated() }
	single { TaskListCreateCommandHandler() }
	single { UserCreateCommandHandler() }
}

val DomainEventSubscribersModule = module {
	single { SendWelcomeEmailOnUserCreated() }
}
