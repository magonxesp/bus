package io.github.magonxesp.bus.domain.event

data class ExampleDomainEvent(override val attributes: Attributes) : DomainEvent<ExampleDomainEvent.Attributes>() {
	constructor(
		exampleName: String,
		exampleAttribute: String
	) : this(Attributes(exampleName, exampleAttribute))

	data class Attributes(
		val exampleName: String,
		val exampleAttribute: String
	)

	override val eventName: String = "example_domain_event"
}
