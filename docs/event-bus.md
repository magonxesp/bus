# Event bus

## Publish domain event

For publish a domain event you need create the domain event class.
The class must meet the following requirements:
* The class must extends the `DomainEvent` class.
* It doesn't have complex types properties, only primitive properties are allowed.
* Must have an empty constructor.

For example, we are going to create the created user domain event.

```kotlin
class UserCreated(
    id: String,
    name: String,
    email: String,
    avatarUrl: String
) : DomainEvent() {
    // the mandatory empty constructor
    constructor() : this(
        id = "",
        name = "",
        email = "",
        avatarUrl = "",
    )

    override val eventName = "user_created"

    val id: String by readOnlyAttribute(id)
    val name: String by readOnlyAttribute(name)
    val email: String by readOnlyAttribute(email)
    val avatarUrl: String by readOnlyAttribute(avatarUrl)
}
```

This domain event has two constructors, the first constructor that helps to create a new immutable instance of the `UserCreated` class
and the second constructor which is empty. Also, we are declared the domain event properties as read only attributes by calling the function
`readOnlyAttribute` with the `by` keyword for override the getters and setters of the declared properties.

### The reason behind the `readOnlyAttribute` delegate

We are declared the properties with the `readOnlyAttribute` delegate for serialization purposes.
It helps to store the value of these properties in the attributes map.

When the domain event is serialized the resulting JSON will be like the following:
```json
{
  "eventId": "e463a0b9-30a1-4a74-9831-cbbf3d752f5f",
  "eventName": "user_created",
  "attributes": {
    "id": "73c9ddb3-6e8c-46e3-b67f-51f0f197097c",
    "name": "John Doe",
    "email": "johndoe@example.com",
    "avatarUrl": "https://awesome-avatar.com/john-doe.png"
  },
  "occurredOn:": "2024-04-02T14:02:27.570Z"
}
```

This is the message content will send the `DomainEventBus` when the domain event is published.
