package io.github.magonxesp.bus.domain.event

class ExampleNestedDomainEvent(
	exampleAttribute: String,
	exampleName: Nested
) : DomainEvent() {
	constructor() : this(
		exampleAttribute = "",
		exampleName = Nested("")
	)

	override val eventName: String = "example_domain_event"

	// var exampleName: Nested by attribute(exampleName) // Not allowed!!
	var exampleAttribute: String by attribute(exampleAttribute)

	data class Nested(
		var value: String
	)
}
