package com.example.pylearn.ui.activity

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.pylearn.data.SampleLearningData
import com.example.pylearn.ui.theme.PyLearnTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ActivityScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val topic =
        SampleLearningData.topics.first { it.id == "variables" }

    private val questions =
        SampleLearningData.getQuestionsForTopic("variables")

    @Test
    fun activityScreen_displaysQuestionAndTopicTitle() {
        composeTestRule.setContent {
            PyLearnTheme {
                ActivityScreen(
                    uiState = ActivityUiState(
                        topic = topic,
                        questions = questions
                    ),
                    onAnswerSelected = {},
                    onSubmitAnswer = {},
                    onNextQuestion = {},
                    onRestartQuiz = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText(topic.title)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(questions.first().questionText)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Question 1 of ${questions.size}")
            .assertIsDisplayed()
    }

    @Test
    fun submitButton_isDisabledWithoutSelectedAnswer() {
        composeTestRule.setContent {
            PyLearnTheme {
                ActivityScreen(
                    uiState = ActivityUiState(
                        topic = topic,
                        questions = questions
                    ),
                    onAnswerSelected = {},
                    onSubmitAnswer = {},
                    onNextQuestion = {},
                    onRestartQuiz = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Submit Answer")
            .assertIsNotEnabled()
    }

    @Test
    fun submitButton_isEnabledWhenAnswerIsSelected() {
        composeTestRule.setContent {
            PyLearnTheme {
                ActivityScreen(
                    uiState = ActivityUiState(
                        topic = topic,
                        questions = questions,
                        selectedAnswerIndex = 0
                    ),
                    onAnswerSelected = {},
                    onSubmitAnswer = {},
                    onNextQuestion = {},
                    onRestartQuiz = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Submit Answer")
            .assertIsEnabled()
    }

    @Test
    fun clickingAnswer_returnsSelectedAnswerIndex() {
        var selectedAnswerIndex: Int? = null
        val firstQuestion = questions.first()

        composeTestRule.setContent {
            PyLearnTheme {
                ActivityScreen(
                    uiState = ActivityUiState(
                        topic = topic,
                        questions = questions
                    ),
                    onAnswerSelected = { answerIndex ->
                        selectedAnswerIndex = answerIndex
                    },
                    onSubmitAnswer = {},
                    onNextQuestion = {},
                    onRestartQuiz = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText(firstQuestion.options.first())
            .performClick()

        assertEquals(
            0,
            selectedAnswerIndex
        )
    }

    @Test
    fun submittedCorrectAnswer_displaysFeedbackAndNextButton() {
        val firstQuestion = questions.first()

        composeTestRule.setContent {
            PyLearnTheme {
                ActivityScreen(
                    uiState = ActivityUiState(
                        topic = topic,
                        questions = questions,
                        selectedAnswerIndex =
                            firstQuestion.correctAnswerIndex,
                        isAnswerSubmitted = true,
                        score = 1
                    ),
                    onAnswerSelected = {},
                    onSubmitAnswer = {},
                    onNextQuestion = {},
                    onRestartQuiz = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Correct")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(firstQuestion.explanation)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Next Question")
            .assertIsDisplayed()
    }

    @Test
    fun submittedIncorrectAnswer_displaysNotQuiteFeedback() {
        val firstQuestion = questions.first()

        val incorrectAnswerIndex =
            firstQuestion.options.indices.first {
                it != firstQuestion.correctAnswerIndex
            }

        composeTestRule.setContent {
            PyLearnTheme {
                ActivityScreen(
                    uiState = ActivityUiState(
                        topic = topic,
                        questions = questions,
                        selectedAnswerIndex = incorrectAnswerIndex,
                        isAnswerSubmitted = true
                    ),
                    onAnswerSelected = {},
                    onSubmitAnswer = {},
                    onNextQuestion = {},
                    onRestartQuiz = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Not quite")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(firstQuestion.explanation)
            .assertIsDisplayed()
    }

    @Test
    fun completedQuiz_displaysScoreAndCompletionButtons() {
        composeTestRule.setContent {
            PyLearnTheme {
                ActivityScreen(
                    uiState = ActivityUiState(
                        topic = topic,
                        questions = questions,
                        score = 3,
                        isQuizComplete = true
                    ),
                    onAnswerSelected = {},
                    onSubmitAnswer = {},
                    onNextQuestion = {},
                    onRestartQuiz = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Activity Complete")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Score: 3 out of ${questions.size}")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Try Again")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Back to Home")
            .assertIsDisplayed()
    }
}