package io.sawmillserver.services

import io.logz.sawmill.Doc
import io.logz.sawmill.ExecutionResult
import io.logz.sawmill.Pipeline
import io.logz.sawmill.PipelineExecutor

class SawmillExecutorService {
    companion object {
        fun executePipeline(pipelineJson: String, vararg logs: MutableMap<String, Any>): List<ExecutorResult> {
            val pipeline = Pipeline.Factory().create(pipelineJson)
            return logs.map { log ->
                val doc = Doc(log)
                ExecutorResult(execute(pipeline, doc), log)
            }
        }

        private fun execute(pipeline: Pipeline, doc: Doc): ExecutionResult {
            return PipelineExecutor().execute(pipeline, doc)
        }
    }
}

data class ExecutorResult (
    val executionResult: ExecutionResult,
    val resultingLog: Map<String, Any>
)