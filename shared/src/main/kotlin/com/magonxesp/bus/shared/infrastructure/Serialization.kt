package com.magonxesp.bus.shared.infrastructure

import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.datetime.Instant

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
