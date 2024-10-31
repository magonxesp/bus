package io.github.magonxesp.bus.infrastructure.shared

import org.json.JSONObject

fun String.uglifyJson() = JSONObject(this.trimIndent()).toString()
