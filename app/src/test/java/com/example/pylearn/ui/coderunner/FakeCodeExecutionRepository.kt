package com.example.pylearn.ui.coderunner

import com.example.pylearn.data.CodeExecutionRepository
import com.example.pylearn.domain.model.CodeExecutionResult

class FakeCodeExecutionRepository : CodeExecutionRepository {

    var resultToReturn: Result<CodeExecutionResult> =
        Result.success(
            CodeExecutionResult(
                output = "Hello from PyLearn!",
                statusDescription = "Accepted",
                executionTime = "0.01",
                memoryUsedKb = 1024,
                isSuccessful = true
            )
        )

    var submittedSourceCode: String? = null
        private set

    var submittedStandardInput: String? = null
        private set

    override suspend fun executePythonCode(
        sourceCode: String,
        standardInput: String?
    ): Result<CodeExecutionResult> {
        submittedSourceCode = sourceCode
        submittedStandardInput = standardInput

        return resultToReturn
    }
}