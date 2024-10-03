package io.github.magonxesp.bus.infrastructure.shared.dependencyinjection

import io.github.magonxesp.bus.domain.command.TaskListCreateCommandHandler
import io.github.magonxesp.bus.domain.command.UserCreateCommandHandler
import io.github.magonxesp.bus.domain.event.CountTotalUsersOnUserCreated
import io.github.magonxesp.bus.domain.event.SendWelcomeEmailOnUserCreated
import kotlin.reflect.KClass

class TestDependencyInjectionContainer : BusDependencyInjectionHelper {
	override fun <T> get(klass: KClass<*>): T = when(klass) {
		CountTotalUsersOnUserCreated::class -> CountTotalUsersOnUserCreated() as T
		SendWelcomeEmailOnUserCreated::class -> SendWelcomeEmailOnUserCreated() as T
		TaskListCreateCommandHandler::class -> TaskListCreateCommandHandler() as T
		UserCreateCommandHandler::class -> UserCreateCommandHandler() as T
		else -> error("$klass not found")
	}
}
