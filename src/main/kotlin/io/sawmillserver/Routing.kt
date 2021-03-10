package io.sawmillserver

import io.sawmillserver.api.healthCheckApi
import io.ktor.application.Application
import io.ktor.http.content.*
import io.ktor.routing.Routing
import io.ktor.routing.routing
import io.sawmillserver.api.sawmillExecutorApi
import java.io.File

fun Application.installRouting() {
    routing {
        apiRoutes()
        landingPage()
    }
}

fun Routing.apiRoutes() {
    healthCheckApi()
    sawmillExecutorApi()
}

fun Routing.landingPage() {
    // serve static content under path "/"
    staticRootFolder = File("landing_page")
    default("home.html") // default file to serve for "/"
    file("home", "home.html")
    static("assets") {
        // serve files as "../assets"
        files("assets") // server files under the folder "assets"
    }
}

