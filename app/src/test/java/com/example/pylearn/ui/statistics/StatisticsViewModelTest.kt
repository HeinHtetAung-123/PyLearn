package com.example.pylearn.ui.statistics

import com.example.pylearn.testing.FakeQuizProgressRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class StatisticsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: FakeQuizProgressRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = FakeQuizProgressRepository()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun emptyRepository_returnsEmptyStatistics() = runTest {
        val viewModel = StatisticsViewModel(repository)

        val collectionJob = backgroundScope.launch(
            UnconfinedTestDispatcher(testScheduler)
        ) {
            viewModel.uiState.collect {}
        }

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertFalse(state.isLoading)
        assertEquals(0, state.completedTopicCount)
        assertEquals(0, state.totalAttempts)
        assertEquals(0, state.averageBestScorePercentage)
        assertEquals(6, state.totalTopicCount)
        assertEquals(0, state.topicStatistics.size)

        collectionJob.cancel()
    }

    @Test
    fun savedProgress_isConvertedToTopicStatistics() = runTest {
        repository.addProgress(
            topicId = "variables",
            score = 3,
            totalQuestions = 4
        )

        val viewModel = StatisticsViewModel(repository)

        val collectionJob = backgroundScope.launch(
            UnconfinedTestDispatcher(testScheduler)
        ) {
            viewModel.uiState.collect {}
        }

        advanceUntilIdle()

        val state = viewModel.uiState.value
        val variables = state.topicStatistics.first()

        assertEquals(1, state.completedTopicCount)
        assertEquals(1, state.totalAttempts)
        assertEquals(75, state.averageBestScorePercentage)
        assertEquals(
            "Variables and Data Types",
            variables.topicTitle
        )
        assertEquals(3, variables.bestScore)
        assertEquals(4, variables.totalQuestions)

        collectionJob.cancel()
    }

    @Test
    fun multipleTopicRecords_calculateSummaryCorrectly() = runTest {
        repository.addProgress(
            topicId = "variables",
            score = 4,
            totalQuestions = 4
        )

        repository.addProgress(
            topicId = "operators",
            score = 2,
            totalQuestions = 4
        )

        val viewModel = StatisticsViewModel(repository)

        val collectionJob = backgroundScope.launch(
            UnconfinedTestDispatcher(testScheduler)
        ) {
            viewModel.uiState.collect {}
        }

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertEquals(2, state.completedTopicCount)
        assertEquals(2, state.totalAttempts)
        assertEquals(75, state.averageBestScorePercentage)
        assertEquals(2, state.topicStatistics.size)

        collectionJob.cancel()
    }
}