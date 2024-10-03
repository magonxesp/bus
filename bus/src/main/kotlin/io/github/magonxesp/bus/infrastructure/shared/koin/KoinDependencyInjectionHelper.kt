package io.github.magonxesp.bus.infrastructure.shared.koin

import io.github.magonxesp.bus.infrastructure.shared.dependencyinjection.BusDependencyInjectionHelper
import org.koin.java.KoinJavaComponent
import kotlin.reflect.KClass

class KoinDependencyInjectionHelper : BusDependencyInjectionHelper {
	override fun <T> get(klass: KClass<*>): T = KoinJavaComponent.get(klass.java)
}
