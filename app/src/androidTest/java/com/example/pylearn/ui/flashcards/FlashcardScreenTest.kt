package com.example.pylearn.ui.flashcards

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.pylearn.data.SampleFlashcardData
import com.example.pylearn.data.SampleLearningData
import com.example.pylearn.ui.theme.PyLearnTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.onAllNodesWithText

class FlashcardScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val topic =
        SampleLearningData.topics.first {
            it.id == "variables"
        }

    private val flashcards =
        SampleFlashcardData.getFlashcardsForTopic(
            "variables"
        )

    @Test
    fun flashcardScreen_displaysPromptAndProgress() {
        composeTestRule.setContent {
            PyLearnTheme {
                FlashcardScreen(
                    uiState = FlashcardUiState(
                        topic = topic,
                        flashcards = flashcards
                    ),
                    onFlipCard = {},
                    onRemembered = {},
                    onReviewAgain = {},
                    onRestartSession = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText(topic.title)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Flashcard 1 of ${flashcards.size}")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Prompt")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(flashcards.first().prompt)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Reveal Answer")
            .assertIsDisplayed()
    }

    @Test
    fun answerButtons_areHiddenBeforeCardIsFlipped() {
        composeTestRule.setContent {
            PyLearnTheme {
                FlashcardScreen(
                    uiState = FlashcardUiState(
                        topic = topic,
                        flashcards = flashcards,
                        isCardFlipped = false
                    ),
                    onFlipCard = {},
                    onRemembered = {},
                    onReviewAgain = {},
                    onRestartSession = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onAllNodesWithText("I Remembered")
            .assertCountEquals(0)

        composeTestRule
            .onAllNodesWithText("Review Again")
            .assertCountEquals(0)
    }

    @Test
    fun flippedCard_displaysAnswerAndRecallButtons() {
        composeTestRule.setContent {
            PyLearnTheme {
                FlashcardScreen(
                    uiState = FlashcardUiState(
                        topic = topic,
                        flashcards = flashcards,
                        isCardFlipped = true
                    ),
                    onFlipCard = {},
                    onRemembered = {},
                    onReviewAgain = {},
                    onRestartSession = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Answer")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(flashcards.first().answer)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("I Remembered")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Review Again")
            .assertIsDisplayed()
    }

    @Test
    fun revealAnswerButton_triggersFlipCallback() {
        var flipRequested = false

        composeTestRule.setContent {
            PyLearnTheme {
                FlashcardScreen(
                    uiState = FlashcardUiState(
                        topic = topic,
                        flashcards = flashcards
                    ),
                    onFlipCard = {
                        flipRequested = true
                    },
                    onRemembered = {},
                    onReviewAgain = {},
                    onRestartSession = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Reveal Answer")
            .performClick()

        assertTrue(flipRequested)
    }

    @Test
    fun rememberedButton_triggersRememberedCallback() {
        var rememberedSelected = false

        composeTestRule.setContent {
            PyLearnTheme {
                FlashcardScreen(
                    uiState = FlashcardUiState(
                        topic = topic,
                        flashcards = flashcards,
                        isCardFlipped = true
                    ),
                    onFlipCard = {},
                    onRemembered = {
                        rememberedSelected = true
                    },
                    onReviewAgain = {},
                    onRestartSession = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("I Remembered")
            .performClick()

        assertTrue(rememberedSelected)
    }

    @Test
    fun reviewAgainButton_triggersReviewCallback() {
        var reviewSelected = false

        composeTestRule.setContent {
            PyLearnTheme {
                FlashcardScreen(
                    uiState = FlashcardUiState(
                        topic = topic,
                        flashcards = flashcards,
                        isCardFlipped = true
                    ),
                    onFlipCard = {},
                    onRemembered = {},
                    onReviewAgain = {
                        reviewSelected = true
                    },
                    onRestartSession = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Review Again")
            .performClick()

        assertTrue(reviewSelected)
    }

    @Test
    fun completedSession_displaysSummary() {
        composeTestRule.setContent {
            PyLearnTheme {
                FlashcardScreen(
                    uiState = FlashcardUiState(
                        topic = topic,
                        flashcards = flashcards,
                        rememberedCount = 2,
                        reviewAgainCount = 1,
                        isSessionComplete = true
                    ),
                    onFlipCard = {},
                    onRemembered = {},
                    onReviewAgain = {},
                    onRestartSession = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Flashcard Review Complete")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("2 of ${flashcards.size}")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(
                "1 card(s) marked for another review."
            )
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Back to Topics")
            .assertIsDisplayed()
    }

    @Test
    fun restartButton_triggersRestartCallback() {
        var restartRequested = false

        composeTestRule.setContent {
            PyLearnTheme {
                FlashcardScreen(
                    uiState = FlashcardUiState(
                        topic = topic,
                        flashcards = flashcards,
                        rememberedCount = flashcards.size,
                        isSessionComplete = true
                    ),
                    onFlipCard = {},
                    onRemembered = {},
                    onReviewAgain = {},
                    onRestartSession = {
                        restartRequested = true
                    },
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Review Again")
            .performClick()

        assertTrue(restartRequested)
    }

    @Test
    fun errorState_displaysErrorAndBackButton() {
        composeTestRule.setContent {
            PyLearnTheme {
                FlashcardScreen(
                    uiState = FlashcardUiState(
                        errorMessage =
                            "No flashcards are available for this topic."
                    ),
                    onFlipCard = {},
                    onRemembered = {},
                    onReviewAgain = {},
                    onRestartSession = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText(
                "No flashcards are available for this topic."
            )
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Back")
            .assertIsDisplayed()
    }
}