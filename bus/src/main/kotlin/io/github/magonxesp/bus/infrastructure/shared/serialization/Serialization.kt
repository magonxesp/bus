package io.github.magonxesp.bus.infrastructure.shared.serialization

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

fun createJacksonObjectMapperInstance() = ObjectMapper().apply {
	registerKotlinModule()
	registerModule(JavaTimeModule())
	registerModule(KotlinxInstantModule)
}
