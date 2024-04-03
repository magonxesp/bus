package io.github.magonxesp.bus.event.infrastructure

import io.github.magonxesp.bus.event.domain.events.ExampleDomainEvent
import io.github.magonxesp.bus.event.domain.events.ExampleNestedDomainEvent
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.datetime.Instant

class DomainEventSerializerTest : FunSpec({

	val domainEvent = ExampleDomainEvent().apply {
		exampleName = "Pepe"
		exampleAttribute = "Lorem ipsum"
		occurredOn = Instant.parse("2024-04-02T14:02:27.570Z")
	}

	val nestedDomainEvent = ExampleNestedDomainEvent().apply {
		exampleName = ExampleNestedDomainEvent.Nested("Pepe")
		exampleAttribute = "Lorem ipsum"
		occurredOn = Instant.parse("2024-04-02T14:02:27.570Z")
	}

	val serializedDomainEvent = "{\"eventName\":\"example_domain_event\"," +
		"\"attributes\":{\"exampleName\":\"Pepe\",\"exampleAttribute\":\"Lorem ipsum\"}" +
		",\"occurredOn\":\"2024-04-02T14:02:27.570Z\"}"

	val serializedNestedDomainEvent = "{\"eventName\":\"example_domain_event\"," +
		"\"attributes\":{\"exampleName\":{\"value\":\"Pepe\"},\"exampleAttribute\":\"Lorem ipsum\"}" +
		",\"occurredOn\":\"2024-04-02T14:02:27.570Z\"}"

	test("it should serialize domain event") {
		val serialized = domainEvent.serializeToJson()

		serialized shouldBe serializedDomainEvent
	}

	test("it should deserialize domain event") {
		val deserialized = serializedDomainEvent.deserializeDomainEvent<ExampleDomainEvent>()

		deserialized shouldBe domainEvent
	}

	test("it should serialize domain event with object attributes") {
		val serialized = nestedDomainEvent.serializeToJson()

		serialized shouldBe serializedNestedDomainEvent
	}

	xtest("it should deserialize domain event with object attributes") {
		val deserialized = serializedNestedDomainEvent.deserializeDomainEvent<ExampleNestedDomainEvent>()

		deserialized shouldBe nestedDomainEvent
	}

})
