package io.github.magonxesp.bus.infrastructure.event

import io.github.magonxesp.bus.IntegrationTestCase
import io.github.magonxesp.bus.domain.event.ExampleDomainEvent
import io.github.magonxesp.bus.domain.event.ExampleNestedDomainEvent
import io.github.magonxesp.bus.infrastructure.shared.uglifyJson
import io.kotest.matchers.shouldBe
import kotlinx.datetime.Instant

class DomainEventSerializerTest : IntegrationTestCase() {
	private val serializer = DomainEventSerializer()

	private val domainEvent = ExampleDomainEvent(
		exampleName = "Pepe",
		exampleAttribute = "Lorem ipsum"
	)

	private val nestedDomainEvent = ExampleNestedDomainEvent(
		exampleName = "Pepe",
		exampleAttribute = "Lorem ipsum",
		exampleNested = ExampleNestedDomainEvent.Nested("Nested value!")
	)

	private val serializedDomainEvent = """
		{
			"eventId": "${domainEvent.eventId}",
			"eventName": "${domainEvent.eventName}",
			"attributes": {
				"exampleName": "Pepe",
				"exampleAttribute": "Lorem ipsum"
			},
			"occurredOn":"${domainEvent.occurredOn}",
			"_type": "${domainEvent::class.qualifiedName}"
		}
	""".uglifyJson()

	private val serializedNestedDomainEvent = """
		{
			"eventId": "${nestedDomainEvent.eventId}",
			"eventName": "${nestedDomainEvent.eventName}",
			"attributes": {
				"exampleName": "Pepe",
				"exampleAttribute": "Lorem ipsum",
				"exampleNested": {
					"value": "Nested value!"
				}
			},
			"occurredOn":"${nestedDomainEvent.occurredOn}",
			"_type": "${nestedDomainEvent::class.qualifiedName}"
		}
	""".uglifyJson()

	init {
		test("it should serialize domain event") {
			val serialized = serializer.serialize(domainEvent)

			serialized shouldBe serializedDomainEvent
		}

		test("it should deserialize domain event") {
			val deserialized = serializer.deserialize(serializedDomainEvent)

			deserialized shouldBe domainEvent
		}

		test("it should serialize domain event with object attributes") {
			val serialized = serializer.serialize(nestedDomainEvent)

			serialized shouldBe serializedNestedDomainEvent
		}

		test("it should deserialize domain event with object attributes") {
			val deserialized = serializer.deserialize(serializedNestedDomainEvent)

			deserialized shouldBe nestedDomainEvent
		}
	}
}
