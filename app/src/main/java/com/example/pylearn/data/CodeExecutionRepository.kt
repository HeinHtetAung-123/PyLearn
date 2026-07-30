package com.example.pylearn.data

import com.example.pylearn.data.remote.CodeSubmissionRequest
import com.example.pylearn.data.remote.Judge0Api
import com.example.pylearn.domain.model.CodeExecutionResult
import kotlinx.coroutines.delay

interface CodeExecutionRepository {

    suspend fun executePythonCode(
        sourceCode: String,
        standardInput: String? = null
    ): Result<CodeExecutionResult>
}

class Judge0CodeExecutionRepository(
    private val judge0Api: Judge0Api
) : CodeExecutionRepository {

    override suspend fun executePythonCode(
        sourceCode: String,
        standardInput: String?
    ): Result<CodeExecutionResult> {
        return runCatching {
            require(sourceCode.isNotBlank()) {
                "Python code cannot be empty."
            }

            val submission = judge0Api.createSubmission(
                request = CodeSubmissionRequest(
                    sourceCode = sourceCode,
                    languageId = PYTHON_LANGUAGE_ID,
                    standardInput = standardInput
                        ?.takeIf { it.isNotBlank() }
                )
            )

            var executionResponse =
                judge0Api.getSubmission(submission.token)

            var pollingAttempt = 0

            while (
                executionResponse.status.id in PROCESSING_STATUS_IDS &&
                pollingAttempt < MAX_POLLING_ATTEMPTS
            ) {
                delay(POLLING_DELAY_MILLIS)

                executionResponse =
                    judge0Api.getSubmission(submission.token)

                pollingAttempt++
            }

            if (executionResponse.status.id in PROCESSING_STATUS_IDS) {
                throw IllegalStateException(
                    "Code execution took too long. Please try again."
                )
            }

            val output = determineOutput(
                standardOutput = executionResponse.stdout,
                standardError = executionResponse.stderr,
                compileOutput = executionResponse.compileOutput,
                message = executionResponse.message,
                statusDescription =
                    executionResponse.status.description
            )

            CodeExecutionResult(
                output = output,
                statusDescription =
                    executionResponse.status.description,
                executionTime = executionResponse.time,
                memoryUsedKb = executionResponse.memory,
                isSuccessful =
                    executionResponse.status.id == ACCEPTED_STATUS_ID
            )
        }
    }

    private fun determineOutput(
        standardOutput: String?,
        standardError: String?,
        compileOutput: String?,
        message: String?,
        statusDescription: String
    ): String {
        return when {
            !standardOutput.isNullOrBlank() ->
                standardOutput.trimEnd()

            !standardError.isNullOrBlank() ->
                standardError.trimEnd()

            !compileOutput.isNullOrBlank() ->
                compileOutput.trimEnd()

            !message.isNullOrBlank() ->
                message.trimEnd()

            statusDescription == "Accepted" ->
                "Program completed without producing output."

            else ->
                statusDescription
        }
    }

    private companion object {
        const val PYTHON_LANGUAGE_ID = 71
        const val ACCEPTED_STATUS_ID = 3

        const val MAX_POLLING_ATTEMPTS = 10
        const val POLLING_DELAY_MILLIS = 1_000L

        val PROCESSING_STATUS_IDS = setOf(1, 2)
    }
}