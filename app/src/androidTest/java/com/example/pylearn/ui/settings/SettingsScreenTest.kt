package com.example.pylearn.ui.settings

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.pylearn.ui.theme.PyLearnTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class SettingsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun settingsScreen_displaysMainOptions() {
        composeTestRule.setContent {
            PyLearnTheme {
                SettingsScreen(
                    uiState = SettingsUiState(
                        isLoading = false
                    ),
                    onDarkModeChanged = {},
                    onLargeTextChanged = {},
                    onConfirmBeforeResetChanged = {},
                    onResetProgressClick = {},
                    onConfirmReset = {},
                    onDismissReset = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Settings")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Dark mode")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Larger text")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Confirm before reset")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Reset Progress")
            .assertIsDisplayed()
    }

    @Test
    fun darkModeSwitch_reflectsEnabledState() {
        composeTestRule.setContent {
            PyLearnTheme {
                SettingsScreen(
                    uiState = SettingsUiState(
                        darkModeEnabled = true,
                        isLoading = false
                    ),
                    onDarkModeChanged = {},
                    onLargeTextChanged = {},
                    onConfirmBeforeResetChanged = {},
                    onResetProgressClick = {},
                    onConfirmReset = {},
                    onDismissReset = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onAllNodesWithText("Dark mode")
            .onFirst()
            .assertIsDisplayed()
    }

    @Test
    fun resetButton_triggersResetRequest() {
        var resetRequested = false

        composeTestRule.setContent {
            PyLearnTheme {
                SettingsScreen(
                    uiState = SettingsUiState(
                        isLoading = false
                    ),
                    onDarkModeChanged = {},
                    onLargeTextChanged = {},
                    onConfirmBeforeResetChanged = {},
                    onResetProgressClick = {
                        resetRequested = true
                    },
                    onConfirmReset = {},
                    onDismissReset = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Reset Progress")
            .performClick()

        assertTrue(resetRequested)
    }

    @Test
    fun resetConfirmationState_displaysDialog() {
        composeTestRule.setContent {
            PyLearnTheme {
                SettingsScreen(
                    uiState = SettingsUiState(
                        showResetConfirmation = true,
                        isLoading = false
                    ),
                    onDarkModeChanged = {},
                    onLargeTextChanged = {},
                    onConfirmBeforeResetChanged = {},
                    onResetProgressClick = {},
                    onConfirmReset = {},
                    onDismissReset = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Reset progress?")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Reset")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Cancel")
            .assertIsDisplayed()
    }

    @Test
    fun confirmingReset_triggersConfirmCallback() {
        var resetConfirmed = false

        composeTestRule.setContent {
            PyLearnTheme {
                SettingsScreen(
                    uiState = SettingsUiState(
                        showResetConfirmation = true,
                        isLoading = false
                    ),
                    onDarkModeChanged = {},
                    onLargeTextChanged = {},
                    onConfirmBeforeResetChanged = {},
                    onResetProgressClick = {},
                    onConfirmReset = {
                        resetConfirmed = true
                    },
                    onDismissReset = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Reset")
            .performClick()

        assertTrue(resetConfirmed)
    }

    @Test
    fun cancellingReset_triggersDismissCallback() {
        var resetDismissed = false

        composeTestRule.setContent {
            PyLearnTheme {
                SettingsScreen(
                    uiState = SettingsUiState(
                        showResetConfirmation = true,
                        isLoading = false
                    ),
                    onDarkModeChanged = {},
                    onLargeTextChanged = {},
                    onConfirmBeforeResetChanged = {},
                    onResetProgressClick = {},
                    onConfirmReset = {},
                    onDismissReset = {
                        resetDismissed = true
                    },
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Cancel")
            .performClick()

        assertTrue(resetDismissed)
    }
}