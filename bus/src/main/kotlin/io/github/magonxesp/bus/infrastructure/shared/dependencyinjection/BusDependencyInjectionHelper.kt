package io.github.magonxesp.bus.infrastructure.shared.dependencyinjection

import kotlin.reflect.KClass

interface BusDependencyInjectionHelper {
	fun <T> get(klass: KClass<*>): T
}
