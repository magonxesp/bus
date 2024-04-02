package io.github.magonxesp.bus.event.domain

class ExampleNestedDomainEvent : DomainEvent() {
	override val eventName: String = "example_domain_event"

	var exampleAttribute: String by attributes
	var exampleName: Nested by attributes

	data class Nested(
		var value: String
	)
}
