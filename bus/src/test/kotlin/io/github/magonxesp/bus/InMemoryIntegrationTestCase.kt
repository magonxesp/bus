package io.github.magonxesp.bus

import io.github.magonxesp.bus.infrastructure.command.koin.inMemoryCommandBusModule
import io.kotest.core.spec.Spec
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

abstract class InMemoryIntegrationTestCase : IntegrationTestCase() {
	private fun setupInMemoryBus() {
		stopKoin()
		startKoin {
			modules(
				CommandHandlersModule,
				DomainEventSubscribersModule,
				inMemoryCommandBusModule {
					basePackage = "io.github.magonxesp.bus"
				},
				inMemoryCommandBusModule {
					basePackage = "io.github.magonxesp.bus"
					async = true
				}
			)
		}
	}

	override suspend fun beforeSpec(spec: Spec) {
		super.beforeSpec(spec)
		setupInMemoryBus()
	}
}
