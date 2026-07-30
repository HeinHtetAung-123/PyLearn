package com.example.pylearn.ui.coderunner

import com.example.pylearn.domain.model.CodeExecutionResult

data class CodeRunnerUiState(
    val sourceCode: String = DEFAULT_CODE,
    val standardInput: String = "",
    val executionResult: CodeExecutionResult? = null,
    val errorMessage: String? = null,
    val isRunning: Boolean = false
) {
    val canRunCode: Boolean
        get() = sourceCode.isNotBlank() && !isRunning

    companion object {
        const val DEFAULT_CODE =
            """message = "Hello from PyLearn!"
print(message)"""
    }
}