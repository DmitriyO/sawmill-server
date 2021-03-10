package io.sawmillserver.api

import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get

fun Routing.sawmillExecutorApi() {
    get("sawmill/execute") {
        val sawmillResponse = mapOf("sawmill" to false, "garzen" to true)
        call.respond(sawmillResponse)
    }
}