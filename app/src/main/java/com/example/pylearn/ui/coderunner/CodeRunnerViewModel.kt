package com.example.pylearn.ui.coderunner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pylearn.data.CodeExecutionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CodeRunnerViewModel(
    private val codeExecutionRepository: CodeExecutionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CodeRunnerUiState())
    val uiState: StateFlow<CodeRunnerUiState> = _uiState.asStateFlow()

    fun updateSourceCode(sourceCode: String) {
        _uiState.update {
            it.copy(
                sourceCode = sourceCode,
                executionResult = null,
                errorMessage = null
            )
        }
    }

    fun updateStandardInput(standardInput: String) {
        _uiState.update {
            it.copy(
                standardInput = standardInput,
                executionResult = null,
                errorMessage = null
            )
        }
    }

    fun runCode() {
        val currentState = _uiState.value

        if (currentState.sourceCode.isBlank() || currentState.isRunning) {
            return
        }

        _uiState.update {
            it.copy(
                isRunning = true,
                executionResult = null,
                errorMessage = null
            )
        }

        viewModelScope.launch {
            val result = codeExecutionRepository.executePythonCode(
                sourceCode = currentState.sourceCode,
                standardInput = currentState.standardInput
                    .takeIf { input -> input.isNotBlank() }
            )

            result.fold(
                onSuccess = { executionResult ->
                    _uiState.update {
                        it.copy(
                            executionResult = executionResult,
                            isRunning = false
                        )
                    }
                },
                onFailure = { throwable ->
                    _uiState.update {
                        it.copy(
                            errorMessage = throwable.message
                                ?: "Python code could not be executed.",
                            isRunning = false
                        )
                    }
                }
            )
        }
    }

    fun resetCode() {
        _uiState.value = CodeRunnerUiState()
    }

    fun clearResult() {
        _uiState.update {
            it.copy(
                executionResult = null,
                errorMessage = null
            )
        }
    }
}