package io.github.magonxesp.bus.event.domain

class ExampleDomainEvent : DomainEvent() {
	override val eventName: String = "example_domain_event"

	var exampleAttribute: String by attributes
	var exampleName: String by attributes
}
