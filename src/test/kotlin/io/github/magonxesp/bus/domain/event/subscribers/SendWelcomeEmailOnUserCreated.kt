package io.github.magonxesp.bus.domain.event.subscribers

import io.github.magonxesp.bus.IntegrationTestCase
import io.github.magonxesp.bus.domain.event.DomainEventSubscriber
import io.github.magonxesp.bus.domain.event.events.UserCreated
import kotlin.io.path.Path

class SendWelcomeEmailOnUserCreated : DomainEventSubscriber<UserCreated> {
	override fun handle(event: UserCreated) {
		Path(IntegrationTestCase.TEST_TMP_DIR, event.eventId).toFile().run {
			writeText(this::class.toString())
		}
	}
}
