package com.example.pylearn.ui.activity

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import com.example.pylearn.testing.FakeQuizProgressRepository


class ActivityViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: ActivityViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        viewModel = ActivityViewModel(
            quizProgressRepository =
                FakeQuizProgressRepository()
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadTopic_withValidTopic_loadsTopicAndQuestions() {
        viewModel.loadTopic("variables")

        val state = viewModel.uiState.value

        assertNotNull(state.topic)
        assertEquals("variables", state.topic?.id)
        assertTrue(state.questions.isNotEmpty())
        assertNull(state.errorMessage)
    }

    @Test
    fun loadTopic_withInvalidTopic_setsErrorMessage() {
        viewModel.loadTopic("invalid_topic")

        val state = viewModel.uiState.value

        assertNull(state.topic)
        assertTrue(state.questions.isEmpty())
        assertEquals(
            "The selected Python topic could not be found.",
            state.errorMessage
        )
    }

    @Test
    fun selectAnswer_updatesSelectedAnswerIndex() {
        viewModel.loadTopic("variables")

        viewModel.selectAnswer(1)

        assertEquals(
            1,
            viewModel.uiState.value.selectedAnswerIndex
        )
    }

    @Test
    fun submitCorrectAnswer_increasesScore() {
        viewModel.loadTopic("variables")

        val correctAnswerIndex =
            viewModel.uiState.value.currentQuestion?.correctAnswerIndex

        assertNotNull(correctAnswerIndex)

        viewModel.selectAnswer(correctAnswerIndex!!)
        viewModel.submitAnswer()

        val state = viewModel.uiState.value

        assertEquals(1, state.score)
        assertTrue(state.isAnswerSubmitted)
    }

    @Test
    fun submitIncorrectAnswer_doesNotIncreaseScore() {
        viewModel.loadTopic("variables")

        val question = viewModel.uiState.value.currentQuestion
        assertNotNull(question)

        val incorrectAnswerIndex =
            question!!.options.indices.first { index ->
                index != question.correctAnswerIndex
            }

        viewModel.selectAnswer(incorrectAnswerIndex)
        viewModel.submitAnswer()

        val state = viewModel.uiState.value

        assertEquals(0, state.score)
        assertTrue(state.isAnswerSubmitted)
    }

    @Test
    fun selectAnswer_afterSubmission_doesNotChangeAnswer() {
        viewModel.loadTopic("variables")

        viewModel.selectAnswer(0)
        viewModel.submitAnswer()
        viewModel.selectAnswer(2)

        assertEquals(
            0,
            viewModel.uiState.value.selectedAnswerIndex
        )
    }

    @Test
    fun moveToNextQuestion_afterSubmission_advancesQuestion() {
        viewModel.loadTopic("variables")

        viewModel.selectAnswer(0)
        viewModel.submitAnswer()
        viewModel.moveToNextQuestion()

        val state = viewModel.uiState.value

        assertEquals(1, state.currentQuestionIndex)
        assertNull(state.selectedAnswerIndex)
        assertFalse(state.isAnswerSubmitted)
    }

    @Test
    fun moveToNextQuestion_withoutSubmission_doesNotAdvance() {
        viewModel.loadTopic("variables")

        viewModel.selectAnswer(0)
        viewModel.moveToNextQuestion()

        assertEquals(
            0,
            viewModel.uiState.value.currentQuestionIndex
        )
    }

    @Test
    fun completingLastQuestion_marksQuizComplete() {
        viewModel.loadTopic("operators")

        while (
            viewModel.uiState.value.currentQuestionIndex <
            viewModel.uiState.value.questions.lastIndex
        ) {
            viewModel.selectAnswer(0)
            viewModel.submitAnswer()
            viewModel.moveToNextQuestion()
        }

        viewModel.selectAnswer(0)
        viewModel.submitAnswer()
        viewModel.moveToNextQuestion()

        assertTrue(
            viewModel.uiState.value.isQuizComplete
        )
    }

    @Test
    fun restartQuiz_resetsQuizState() {
        viewModel.loadTopic("variables")

        viewModel.selectAnswer(0)
        viewModel.submitAnswer()
        viewModel.moveToNextQuestion()

        viewModel.restartQuiz()

        val state = viewModel.uiState.value

        assertEquals(0, state.currentQuestionIndex)
        assertEquals(0, state.score)
        assertNull(state.selectedAnswerIndex)
        assertFalse(state.isAnswerSubmitted)
        assertFalse(state.isQuizComplete)
    }

    @Test
    fun finishingQuizMultipleTimes_savesResultOnlyOnce() {
        val fakeRepository = FakeQuizProgressRepository()

        viewModel = ActivityViewModel(
            quizProgressRepository = fakeRepository
        )

        viewModel.loadTopic("operators")

        while (
            viewModel.uiState.value.currentQuestionIndex <
            viewModel.uiState.value.questions.lastIndex
        ) {
            viewModel.selectAnswer(0)
            viewModel.submitAnswer()
            viewModel.moveToNextQuestion()
        }

        viewModel.selectAnswer(0)
        viewModel.submitAnswer()

        viewModel.moveToNextQuestion()
        viewModel.moveToNextQuestion()
        viewModel.moveToNextQuestion()

        assertEquals(
            1,
            fakeRepository.saveCallCount
        )
    }
}