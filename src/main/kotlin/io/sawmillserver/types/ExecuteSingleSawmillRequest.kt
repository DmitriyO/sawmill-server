package io.sawmillserver.types

data class ExecuteSingleSawmillRequest (
    val log: Map<String, Any>,
    val pipeline: String
)

data class ExecuteMultipleSawmillRequest (
    val logs: List<Map<String, Any>>,
    val pipeline: String
)