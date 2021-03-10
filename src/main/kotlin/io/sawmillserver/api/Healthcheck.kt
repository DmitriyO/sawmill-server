package io.sawmillserver.api

import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get

fun Routing.healthCheckApi() {
    get("health") {
        val healthStatus = mapOf("status" to "ok")
        call.respond(healthStatus)
    }
}