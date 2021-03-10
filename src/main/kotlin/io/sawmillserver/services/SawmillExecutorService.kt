package io.sawmillserver.services

import io.logz.sawmill.Doc
import io.logz.sawmill.ExecutionResult
import io.logz.sawmill.Pipeline
import io.logz.sawmill.PipelineExecutor

class SawmillExecutorService {
    companion object {
        fun executePipeline(pipelineJson: String, vararg logs: MutableMap<String, Any>): List<PipelineExecutionResult> {
            val pipeline = try {
                Pipeline.Factory().create(pipelineJson)
            } catch (e: Exception) {

                return logs.map { PipelineExecutionResult(it, false, e.message) }
            }

            return logs.map { log ->
                val doc = Doc(log)
                val result = execute(pipeline, doc)
                if (result.isSucceeded) {
                    PipelineExecutionResult(log, true)
                } else {
                    PipelineExecutionResult(log, false, result.error.toString())
                }

            }
        }

        private fun execute(pipeline: Pipeline, doc: Doc): ExecutionResult {
            return try {
                PipelineExecutor().execute(pipeline, doc)
            } catch (t: Throwable) {
                throw t
            }
        }
    }
}

data class PipelineExecutionResult (
    val resultingLog: Map<String, Any>,
    val successful: Boolean = true,
    val error: String? = null
)