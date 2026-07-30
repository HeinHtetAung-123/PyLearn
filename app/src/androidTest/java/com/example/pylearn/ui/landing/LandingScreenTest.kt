package com.example.pylearn.ui.landing

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.pylearn.data.SampleLearningData
import com.example.pylearn.ui.theme.PyLearnTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class LandingScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun landingScreen_displaysMainNavigationButtons() {
        composeTestRule.setContent {
            PyLearnTheme {
                LandingScreen(
                    uiState = LandingUiState(
                        topics = SampleLearningData.topics,
                        isLoading = false
                    ),
                    onTopicClick = {},
                    onStatisticsClick = {},
                    onSettingsClick = {},
                    onCodeRunnerClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("PyLearn")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Statistics")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Settings")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Open Python Code Runner")
            .assertIsDisplayed()
    }

    @Test
    fun landingScreen_displaysPythonTopics() {
        composeTestRule.setContent {
            PyLearnTheme {
                LandingScreen(
                    uiState = LandingUiState(
                        topics = SampleLearningData.topics,
                        isLoading = false
                    ),
                    onTopicClick = {},
                    onStatisticsClick = {},
                    onSettingsClick = {},
                    onCodeRunnerClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Variables and Data Types")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Operators")
            .assertIsDisplayed()
    }

    @Test
    fun topicWithoutProgress_displaysNotStarted() {
        composeTestRule.setContent {
            PyLearnTheme {
                LandingScreen(
                    uiState = LandingUiState(
                        topics = listOf(
                            SampleLearningData.topics.first()
                        ),
                        isLoading = false
                    ),
                    onTopicClick = {},
                    onStatisticsClick = {},
                    onSettingsClick = {},
                    onCodeRunnerClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Not started")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Start Topic")
            .assertIsDisplayed()
    }

    @Test
    fun completedTopic_displaysSavedProgress() {
        val topic = SampleLearningData.topics.first()

        composeTestRule.setContent {
            PyLearnTheme {
                LandingScreen(
                    uiState = LandingUiState(
                        topics = listOf(topic),
                        progressByTopicId = mapOf(
                            topic.id to TopicProgressSummary(
                                bestScore = 3,
                                totalQuestions = 4,
                                attemptCount = 2,
                                isCompleted = true
                            )
                        ),
                        isLoading = false
                    ),
                    onTopicClick = {},
                    onStatisticsClick = {},
                    onSettingsClick = {},
                    onCodeRunnerClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Completed")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Best score: 3 of 4 (75%)")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Attempts: 2")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Practice Again")
            .assertIsDisplayed()
    }

    @Test
    fun clickingStartTopic_returnsSelectedTopic() {
        val topic = SampleLearningData.topics.first()
        var selectedTopicId: String? = null

        composeTestRule.setContent {
            PyLearnTheme {
                LandingScreen(
                    uiState = LandingUiState(
                        topics = listOf(topic),
                        isLoading = false
                    ),
                    onTopicClick = { selectedTopic ->
                        selectedTopicId = selectedTopic.id
                    },
                    onStatisticsClick = {},
                    onSettingsClick = {},
                    onCodeRunnerClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Start Topic")
            .performClick()

        assertEquals(
            topic.id,
            selectedTopicId
        )
    }
}