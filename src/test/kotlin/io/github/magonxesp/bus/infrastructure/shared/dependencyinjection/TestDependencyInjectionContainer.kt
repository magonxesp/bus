package io.github.magonxesp.bus.infrastructure.shared.dependencyinjection

import io.github.magonxesp.bus.domain.event.subscribers.CountTotalUsersOnUserCreated
import io.github.magonxesp.bus.domain.event.subscribers.SendWelcomeEmailOnUserCreated
import kotlin.reflect.KClass

class TestDependencyInjectionContainer : BusDependencyInjectionHelper {
	override fun <T> get(klass: KClass<*>): T = when(klass) {
		CountTotalUsersOnUserCreated::class -> CountTotalUsersOnUserCreated() as T
		SendWelcomeEmailOnUserCreated::class -> SendWelcomeEmailOnUserCreated() as T
		else -> error("$klass not found")
	}
}
