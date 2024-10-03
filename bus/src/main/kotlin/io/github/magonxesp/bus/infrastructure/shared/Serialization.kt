package io.github.magonxesp.bus.infrastructure.shared

import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.datetime.Instant
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

fun createJacksonObjectMapperInstance() = ObjectMapper().apply {
	registerKotlinModule()
	registerModule(JavaTimeModule())
}

fun createSharedMoshiInstance(extras: Moshi.Builder.() -> Unit = {}): Moshi {
    val builder = Moshi.Builder()
        .add(KotlinInstantAdapter())
        .addLast(KotlinJsonAdapterFactory())

    builder.extras()
    return builder.build()
}

class KotlinInstantAdapter {
	@ToJson
	fun toJson(instant: Instant): String =
		instant.toString()

	@FromJson
	fun fromJson(dateTime: String): Instant =
		Instant.parse(dateTime)
}
