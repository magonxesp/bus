package io.github.magonxesp.bus.domain.event.events

import io.github.magonxesp.bus.domain.event.DomainEvent

class ExampleDomainEvent(
	exampleName: String,
	exampleAttribute: String
) : DomainEvent() {
	constructor() : this(
		exampleAttribute = "",
		exampleName = ""
	)

	override val eventName: String = "example_domain_event"

	var exampleName: String by attribute(exampleName)
	var exampleAttribute: String by attribute(exampleAttribute)
}
