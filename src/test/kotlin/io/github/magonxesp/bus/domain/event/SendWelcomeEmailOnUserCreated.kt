package io.github.magonxesp.bus.domain.event

import io.github.magonxesp.bus.IntegrationTestCase
import io.github.magonxesp.bus.IntegrationTestCase.Companion.notifyConsumed
import org.slf4j.LoggerFactory
import kotlin.io.path.Path
import kotlin.io.path.writeText

class SendWelcomeEmailOnUserCreated : DomainEventSubscriber<UserCreated> {
	private val logger = LoggerFactory.getLogger(this::class.java)

	override fun handle(event: UserCreated) {
		logger.debug("Domain event subscriber {} fired", this::class)
		notifyConsumed(event)
		logger.debug("Domain event subscriber {} finish", this::class)
	}
}
