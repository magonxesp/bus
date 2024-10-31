package io.github.magonxesp.bus.infrastructure.shared.koin

import io.github.magonxesp.bus.infrastructure.shared.dependencyinjection.BusDependencyInjectionHelper
import org.koin.core.module.Module
import org.koin.dsl.bind

fun Module.defaultDependencyInjection() {
	single { KoinDependencyInjectionHelper() } bind BusDependencyInjectionHelper::class
}
