package io.github.magonxesp.example.inmemorybus

import io.github.magonxesp.example.inmemorybus.plugins.*
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
