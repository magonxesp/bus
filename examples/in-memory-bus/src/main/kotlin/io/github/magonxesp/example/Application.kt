package io.github.magonxesp.example

import io.github.magonxesp.example.plugins.configureDatabase
import io.github.magonxesp.example.plugins.configureDependencyInjection
import io.github.magonxesp.example.plugins.configureRouting
import io.github.magonxesp.example.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
	configureDependencyInjection()
    configureSerialization()
    configureDatabase()
    //configureMonitoring()
    configureRouting()
}
