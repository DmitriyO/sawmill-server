package io.sawmillserver

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.request.path
import io.ktor.response.respond
import org.slf4j.event.Level

fun Application.apiModule() {
    install(DefaultHeaders)
    installContentNegotiation()
    installCallLogging()
    installStatusPages()
    installRouting()
}

private fun Application.installContentNegotiation() {
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            registerModule(JavaTimeModule())
            registerModule(KotlinModule())
        }
    }
}

private fun Application.installCallLogging() {
    install(CallLogging) {
        level = Level.DEBUG
        mdc("path") { call -> call.request.path() }
//        filter { call -> call.request.path().startsWith("/someSection") }
    }
}

private fun Application.installStatusPages() {
    install(StatusPages) {
//        exception<ApiException> { error ->
////            println("error occured: $error. ${error.stackTrace}")
////            val response = mapOf("status" to error.statusCode, "error" to error.message)
////
////            call.respond(error.statusCode, response)
////            // this may have been added in some point that returning response object didn't work well, remove if unnecessary
//////            call.respond(TextContent("${error.statusCode} ${error.message}", ContentType.Text.Plain.withCharset(Charsets.UTF_8), error.statusCode))
////        }
        exception<Exception> { error ->
            println("unexpected error occurred: $error. ${error.stackTrace}")
            val response = mapOf("status" to HttpStatusCode.InternalServerError, "error" to "internal error occurred")

            call.respond(HttpStatusCode.InternalServerError, response)
        }
    }
}

