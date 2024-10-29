package io.github.magonxesp.bus.infrastructure.command

import io.github.magonxesp.bus.infrastructure.shared.serialization.createJacksonObjectMapperInstance

private val objectMapper = createJacksonObjectMapperInstance()

fun DispatchedCommand.serializeToJson() = objectMapper.writeValueAsString(this)
fun String.deserializeDispatchedCommand() = objectMapper.readValue(this, DispatchedCommand::class.java)
