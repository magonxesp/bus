package com.magonxesp.bus.shared.infrastructure

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

fun createSharedMoshiInstance(extras: Moshi.Builder.() -> Unit = {}): Moshi {
    val builder = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())

    builder.extras()
    return builder.build()
}
