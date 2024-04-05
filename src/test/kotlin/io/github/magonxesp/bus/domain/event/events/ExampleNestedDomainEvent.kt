package io.github.magonxesp.bus.domain.event.events

import io.github.magonxesp.bus.domain.event.DomainEvent

class ExampleNestedDomainEvent(
	exampleAttribute: String,
	exampleName: Nested
) : DomainEvent() {
	override val eventName: String = "example_domain_event"

	var exampleName: Nested by attribute(exampleName)
	var exampleAttribute: String by attribute(exampleAttribute)

	data class Nested(
		var value: String
	)
}
