package io.sawmillserver.api

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.post
import io.sawmillserver.services.SawmillExecutorService
import io.sawmillserver.types.ExecuteMultipleSawmillRequest
import io.sawmillserver.types.ExecuteSingleSawmillRequest

fun Routing.sawmillExecutorApi() {
    post ("sawmill/execute-single") {
        val request = call.receive<ExecuteSingleSawmillRequest>()
        val log = request.log.toMutableMap()
        val result = SawmillExecutorService.executePipeline(request.pipeline, log).single()

        if (!result.executionResult.isSucceeded) {
            call.respond(HttpStatusCode.BadRequest, mapOf("reason" to "garzen"))
        }

        call.respond(log)
    }

    post ("sawmill/execute-multiple") {
        val request = call.receive<ExecuteMultipleSawmillRequest>()
        val logs = request.logs.map { it.toMutableMap() }
        val results = SawmillExecutorService.executePipeline(request.pipeline, *logs.toTypedArray())

        call.respond(results)
    }
}