package io.github.magonxesp.bus.domain.event

data class ExampleNestedDomainEvent(override val attributes: Attributes) : DomainEvent<ExampleNestedDomainEvent.Attributes>() {
	constructor(
		exampleName: String,
		exampleAttribute: String,
		exampleNested: Nested
	) : this(Attributes(
		exampleAttribute = exampleAttribute,
		exampleName = exampleName,
		exampleNested = exampleNested
	))

	data class Attributes(
		val exampleName: String,
		val exampleAttribute: String,
		val exampleNested: Nested
	)

	data class Nested(
		var value: String
	)

	override val eventName: String = "example_domain_event"
}
