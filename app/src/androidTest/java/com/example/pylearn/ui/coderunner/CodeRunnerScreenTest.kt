package com.example.pylearn.ui.coderunner

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import com.example.pylearn.domain.model.CodeExecutionResult
import com.example.pylearn.ui.theme.PyLearnTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import androidx.compose.ui.test.performScrollTo

class CodeRunnerScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun codeRunnerScreen_displaysMainContent() {
        composeTestRule.setContent {
            PyLearnTheme {
                CodeRunnerScreen(
                    uiState = CodeRunnerUiState(),
                    onSourceCodeChanged = {},
                    onStandardInputChanged = {},
                    onRunCode = {},
                    onResetCode = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Python Code Runner")
            .performScrollTo()
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("External code execution")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Run Code")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Reset")
            .assertIsDisplayed()
    }

    @Test
    fun runButton_isEnabledWhenCodeIsAvailable() {
        composeTestRule.setContent {
            PyLearnTheme {
                CodeRunnerScreen(
                    uiState = CodeRunnerUiState(
                        sourceCode = "print(\"Hello\")"
                    ),
                    onSourceCodeChanged = {},
                    onStandardInputChanged = {},
                    onRunCode = {},
                    onResetCode = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Run Code")
            .assertIsEnabled()
    }

    @Test
    fun runButton_isDisabledWhenCodeIsBlank() {
        composeTestRule.setContent {
            PyLearnTheme {
                CodeRunnerScreen(
                    uiState = CodeRunnerUiState(
                        sourceCode = ""
                    ),
                    onSourceCodeChanged = {},
                    onStandardInputChanged = {},
                    onRunCode = {},
                    onResetCode = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Run Code")
            .assertIsNotEnabled()
    }

    @Test
    fun clickingRunCode_triggersCallback() {
        var wasRunClicked = false

        composeTestRule.setContent {
            PyLearnTheme {
                CodeRunnerScreen(
                    uiState = CodeRunnerUiState(
                        sourceCode = "print(1)"
                    ),
                    onSourceCodeChanged = {},
                    onStandardInputChanged = {},
                    onRunCode = {
                        wasRunClicked = true
                    },
                    onResetCode = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Run Code")
            .performClick()

        assertTrue(wasRunClicked)
    }

    @Test
    fun editingSourceCode_triggersCallback() {
        var updatedCode = ""

        composeTestRule.setContent {
            PyLearnTheme {
                CodeRunnerScreen(
                    uiState = CodeRunnerUiState(),
                    onSourceCodeChanged = {
                        updatedCode = it
                    },
                    onStandardInputChanged = {},
                    onRunCode = {},
                    onResetCode = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Python code")
            .performTextReplacement("print(\"Updated\")")

        assertEquals(
            "print(\"Updated\")",
            updatedCode
        )
    }

    @Test
    fun successfulExecution_displaysOutputAndStatus() {
        composeTestRule.setContent {
            PyLearnTheme {
                CodeRunnerScreen(
                    uiState = CodeRunnerUiState(
                        executionResult = CodeExecutionResult(
                            output = "Hello from Python",
                            statusDescription = "Accepted",
                            executionTime = "0.01",
                            memoryUsedKb = 1024,
                            isSuccessful = true
                        )
                    ),
                    onSourceCodeChanged = {},
                    onStandardInputChanged = {},
                    onRunCode = {},
                    onResetCode = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Execution successful")
            .performScrollTo()
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Status: Accepted")
            .performScrollTo()
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Hello from Python")
            .performScrollTo()
            .assertIsDisplayed()
    }

    @Test
    fun failedExecution_displaysExecutionIssue() {
        composeTestRule.setContent {
            PyLearnTheme {
                CodeRunnerScreen(
                    uiState = CodeRunnerUiState(
                        executionResult = CodeExecutionResult(
                            output = "NameError",
                            statusDescription = "Runtime Error",
                            isSuccessful = false
                        )
                    ),
                    onSourceCodeChanged = {},
                    onStandardInputChanged = {},
                    onRunCode = {},
                    onResetCode = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Execution issue")
            .performScrollTo()
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("NameError")
            .performScrollTo()
            .assertIsDisplayed()
    }

    @Test
    fun repositoryFailure_displaysErrorCard() {
        composeTestRule.setContent {
            PyLearnTheme {
                CodeRunnerScreen(
                    uiState = CodeRunnerUiState(
                        errorMessage =
                            "Unable to connect to execution service."
                    ),
                    onSourceCodeChanged = {},
                    onStandardInputChanged = {},
                    onRunCode = {},
                    onResetCode = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Unable to run code")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(
                "Unable to connect to execution service."
            )
            .assertIsDisplayed()
    }
}