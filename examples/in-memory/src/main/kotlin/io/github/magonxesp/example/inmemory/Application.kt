package io.github.magonxesp.example.inmemory

import io.github.magonxesp.example.plugins.configureDatabase
import io.github.magonxesp.example.plugins.configureRouting
import io.github.magonxesp.example.plugins.configureSerialization
import io.github.magonxesp.example.plugins.configureEnvironment
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
	configureEnvironment()
	configureDependencyInjection()
    configureSerialization()
    configureDatabase()
    //configureMonitoring()
    configureRouting()
}
