package com.example.pylearn.data

import com.example.pylearn.data.remote.CodeExecutionResponse
import com.example.pylearn.data.remote.CodeSubmissionRequest
import com.example.pylearn.data.remote.ExecutionStatus
import com.example.pylearn.data.remote.Judge0Api
import com.example.pylearn.data.remote.SubmissionTokenResponse
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CodeExecutionRepositoryTest {

    @Test
    fun executePythonCode_withSuccessfulResponse_returnsOutput() = runBlocking {
        val fakeApi = FakeJudge0Api(
            executionResponse = CodeExecutionResponse(
                stdout = "Hello from Python\n",
                status = ExecutionStatus(
                    id = 3,
                    description = "Accepted"
                ),
                time = "0.01",
                memory = 1024
            )
        )

        val repository = Judge0CodeExecutionRepository(
            judge0Api = fakeApi
        )

        val result = repository.executePythonCode(
            sourceCode = "print(\"Hello from Python\")"
        )

        assertTrue(result.isSuccess)

        val executionResult = result.getOrThrow()

        assertEquals(
            "Hello from Python",
            executionResult.output
        )
        assertEquals(
            "Accepted",
            executionResult.statusDescription
        )
        assertEquals(
            "0.01",
            executionResult.executionTime
        )
        assertEquals(
            1024,
            executionResult.memoryUsedKb
        )
        assertTrue(executionResult.isSuccessful)
    }

    @Test
    fun executePythonCode_withRuntimeError_returnsErrorOutput() = runBlocking {
        val fakeApi = FakeJudge0Api(
            executionResponse = CodeExecutionResponse(
                stderr = "NameError: name 'message' is not defined\n",
                status = ExecutionStatus(
                    id = 11,
                    description = "Runtime Error (NZEC)"
                )
            )
        )

        val repository = Judge0CodeExecutionRepository(
            judge0Api = fakeApi
        )

        val result = repository.executePythonCode(
            sourceCode = "print(message)"
        )

        assertTrue(result.isSuccess)

        val executionResult = result.getOrThrow()

        assertEquals(
            "NameError: name 'message' is not defined",
            executionResult.output
        )
        assertFalse(executionResult.isSuccessful)
    }

    @Test
    fun executePythonCode_withBlankCode_returnsFailure() = runBlocking {
        val fakeApi = FakeJudge0Api(
            executionResponse = CodeExecutionResponse(
                stdout = null,
                status = ExecutionStatus(
                    id = 3,
                    description = "Accepted"
                )
            )
        )

        val repository = Judge0CodeExecutionRepository(
            judge0Api = fakeApi
        )

        val result = repository.executePythonCode(
            sourceCode = "   "
        )

        assertTrue(result.isFailure)
        assertEquals(
            "Python code cannot be empty.",
            result.exceptionOrNull()?.message
        )
        assertEquals(
            0,
            fakeApi.submissionCount
        )
    }

    @Test
    fun executePythonCode_withoutPrintedOutput_returnsCompletionMessage() =
        runBlocking {
            val fakeApi = FakeJudge0Api(
                executionResponse = CodeExecutionResponse(
                    stdout = null,
                    status = ExecutionStatus(
                        id = 3,
                        description = "Accepted"
                    )
                )
            )

            val repository = Judge0CodeExecutionRepository(
                judge0Api = fakeApi
            )

            val result = repository.executePythonCode(
                sourceCode = "number = 10"
            )

            assertTrue(result.isSuccess)
            assertEquals(
                "Program completed without producing output.",
                result.getOrThrow().output
            )
        }

    private class FakeJudge0Api(
        private val executionResponse: CodeExecutionResponse
    ) : Judge0Api {

        var submissionCount: Int = 0
            private set

        override suspend fun createSubmission(
            base64Encoded: Boolean,
            wait: Boolean,
            request: CodeSubmissionRequest
        ): SubmissionTokenResponse {
            submissionCount++

            return SubmissionTokenResponse(
                token = "fake-submission-token"
            )
        }

        override suspend fun getSubmission(
            token: String,
            base64Encoded: Boolean,
            fields: String
        ): CodeExecutionResponse {
            return executionResponse
        }
    }
}