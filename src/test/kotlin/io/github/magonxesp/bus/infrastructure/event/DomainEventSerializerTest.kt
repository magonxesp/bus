package io.github.magonxesp.bus.infrastructure.event

import io.github.magonxesp.bus.IntegrationTestCase
import io.github.magonxesp.bus.domain.event.events.ExampleDomainEvent
import io.github.magonxesp.bus.domain.event.events.ExampleNestedDomainEvent
import io.kotest.matchers.shouldBe
import kotlinx.datetime.Instant

class DomainEventSerializerTest : IntegrationTestCase() {
	private val domainEvent = ExampleDomainEvent(
		exampleName = "Pepe",
		exampleAttribute = "Lorem ipsum"
	).apply {
		eventId = "e463a0b9-30a1-4a74-9831-cbbf3d752f5f"
		occurredOn = Instant.parse("2024-04-02T14:02:27.570Z")
	}

	private val nestedDomainEvent = ExampleNestedDomainEvent(
		exampleName = ExampleNestedDomainEvent.Nested("Pepe"),
		exampleAttribute = "Lorem ipsum"
	).apply {
		eventId = "e44b7c5d-157a-406f-bd54-bcf5c64ff0ff"
		occurredOn = Instant.parse("2024-04-02T14:02:27.570Z")
	}

	private val serializedDomainEvent = "{" +
		"\"eventId\":\"e463a0b9-30a1-4a74-9831-cbbf3d752f5f\"," +
		"\"eventName\":\"example_domain_event\"," +
		"\"attributes\":{" +
		"\"exampleName\":\"Pepe\"," +
		"\"exampleAttribute\":\"Lorem ipsum\"" +
		"}," +
		"\"occurredOn\":\"2024-04-02T14:02:27.570Z\"" +
		"}"

	private val serializedNestedDomainEvent = "{" +
		"\"eventId\":\"e44b7c5d-157a-406f-bd54-bcf5c64ff0ff\"," +
		"\"eventName\":\"example_domain_event\"," +
		"\"attributes\":{" +
		"\"exampleName\":{\"value\":\"Pepe\"}," +
		"\"exampleAttribute\":\"Lorem ipsum\"" +
		"}," +
		"\"occurredOn\":\"2024-04-02T14:02:27.570Z\"" +
		"}"

	init {
		test("it should serialize domain event") {
			val serialized = domainEvent.serializeToJson()

			serialized shouldBe serializedDomainEvent
		}

		test("it should deserialize domain event") {
			val deserialized = serializedDomainEvent.deserializeDomainEvent<ExampleDomainEvent>()

			deserialized?.eventId shouldBe domainEvent.eventId
			deserialized?.eventName shouldBe domainEvent.eventName
			deserialized?.occurredOn shouldBe domainEvent.occurredOn
			deserialized?.attributes shouldBe domainEvent.attributes
		}

		xtest("it should serialize domain event with object attributes") {
			val serialized = nestedDomainEvent.serializeToJson()

			serialized shouldBe serializedNestedDomainEvent
		}

		xtest("it should deserialize domain event with object attributes") {
			val deserialized = serializedNestedDomainEvent.deserializeDomainEvent<ExampleNestedDomainEvent>()

			deserialized?.eventId shouldBe nestedDomainEvent.eventId
			deserialized?.eventName shouldBe nestedDomainEvent.eventName
			deserialized?.occurredOn shouldBe nestedDomainEvent.occurredOn
			deserialized?.attributes shouldBe nestedDomainEvent.attributes
		}
	}
}
