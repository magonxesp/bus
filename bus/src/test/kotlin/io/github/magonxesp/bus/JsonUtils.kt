package io.github.magonxesp.bus

import org.json.JSONObject

fun String.uglifyJson() = JSONObject(this.trimIndent()).toString()
