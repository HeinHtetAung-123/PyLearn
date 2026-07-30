package com.example.pylearn.ui.coderunner

import com.example.pylearn.domain.model.CodeExecutionResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CodeRunnerViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var repository: FakeCodeExecutionRepository
    private lateinit var viewModel: CodeRunnerViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        repository = FakeCodeExecutionRepository()

        viewModel = CodeRunnerViewModel(
            codeExecutionRepository = repository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialState_containsDefaultPythonCode() {
        val state = viewModel.uiState.value

        assertTrue(state.sourceCode.isNotBlank())
        assertFalse(state.isRunning)
        assertNull(state.executionResult)
        assertNull(state.errorMessage)
    }

    @Test
    fun updateSourceCode_updatesCodeAndClearsPreviousResult() {
        viewModel.runCode()

        viewModel.updateSourceCode(
            "print(\"Updated\")"
        )

        val state = viewModel.uiState.value

        assertEquals(
            "print(\"Updated\")",
            state.sourceCode
        )
        assertNull(state.executionResult)
        assertNull(state.errorMessage)
    }

    @Test
    fun updateStandardInput_updatesInput() {
        viewModel.updateStandardInput("Alex")

        assertEquals(
            "Alex",
            viewModel.uiState.value.standardInput
        )
    }

    @Test
    fun runCode_withSuccessfulResult_displaysExecutionResult() {
        viewModel.updateSourceCode(
            "print(\"Hello\")"
        )

        viewModel.runCode()

        val state = viewModel.uiState.value

        assertFalse(state.isRunning)
        assertNull(state.errorMessage)
        assertEquals(
            "Hello from PyLearn!",
            state.executionResult?.output
        )
        assertTrue(
            state.executionResult?.isSuccessful == true
        )
    }

    @Test
    fun runCode_sendsSourceCodeAndStandardInputToRepository() {
        viewModel.updateSourceCode(
            "name = input()\nprint(name)"
        )
        viewModel.updateStandardInput("Alex")

        viewModel.runCode()

        assertEquals(
            "name = input()\nprint(name)",
            repository.submittedSourceCode
        )
        assertEquals(
            "Alex",
            repository.submittedStandardInput
        )
    }

    @Test
    fun runCode_withBlankStandardInput_sendsNullInput() {
        viewModel.updateSourceCode(
            "print(\"Hello\")"
        )
        viewModel.updateStandardInput("   ")

        viewModel.runCode()

        assertNull(repository.submittedStandardInput)
    }

    @Test
    fun runCode_withFailure_displaysErrorMessage() {
        repository.resultToReturn = Result.failure(
            IllegalStateException(
                "Unable to connect to code execution service."
            )
        )

        viewModel.runCode()

        val state = viewModel.uiState.value

        assertFalse(state.isRunning)
        assertNull(state.executionResult)
        assertEquals(
            "Unable to connect to code execution service.",
            state.errorMessage
        )
    }

    @Test
    fun resetCode_restoresInitialState() {
        viewModel.updateSourceCode("print(123)")
        viewModel.updateStandardInput("Test")
        viewModel.runCode()

        viewModel.resetCode()

        val state = viewModel.uiState.value

        assertEquals(
            CodeRunnerUiState.DEFAULT_CODE,
            state.sourceCode
        )
        assertEquals("", state.standardInput)
        assertNull(state.executionResult)
        assertNull(state.errorMessage)
        assertFalse(state.isRunning)
    }

    @Test
    fun clearResult_removesResultAndError() {
        repository.resultToReturn = Result.failure(
            IllegalStateException("Execution failed")
        )

        viewModel.runCode()
        viewModel.clearResult()

        val state = viewModel.uiState.value

        assertNull(state.executionResult)
        assertNull(state.errorMessage)
    }

    @Test
    fun blankCode_cannotBeExecuted() {
        viewModel.updateSourceCode("   ")

        viewModel.runCode()

        assertNull(repository.submittedSourceCode)
        assertFalse(viewModel.uiState.value.isRunning)
    }
}