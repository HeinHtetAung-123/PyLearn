package com.example.pylearn.ui.settings

import com.example.pylearn.testing.FakeQuizProgressRepository
import com.example.pylearn.testing.FakeSettingsRepository
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
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var settingsRepository: FakeSettingsRepository
    private lateinit var progressRepository: FakeQuizProgressRepository
    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        settingsRepository = FakeSettingsRepository()
        progressRepository = FakeQuizProgressRepository()

        viewModel = SettingsViewModel(
            settingsRepository = settingsRepository,
            quizProgressRepository = progressRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun enablingDarkMode_updatesUiState() = runTest {
        val collectionJob = backgroundScope.launch(
            UnconfinedTestDispatcher(testScheduler)
        ) {
            viewModel.uiState.collect {}
        }

        viewModel.setDarkModeEnabled(true)
        advanceUntilIdle()

        assertTrue(
            viewModel.uiState.value.darkModeEnabled
        )

        collectionJob.cancel()
    }

    @Test
    fun enablingLargeText_updatesUiState() = runTest {
        val collectionJob = backgroundScope.launch(
            UnconfinedTestDispatcher(testScheduler)
        ) {
            viewModel.uiState.collect {}
        }

        viewModel.setLargeTextEnabled(true)
        advanceUntilIdle()

        assertTrue(
            viewModel.uiState.value.largeTextEnabled
        )

        collectionJob.cancel()
    }

    @Test
    fun resetRequest_withConfirmationEnabled_showsDialog() = runTest {
        val collectionJob = backgroundScope.launch(
            UnconfinedTestDispatcher(testScheduler)
        ) {
            viewModel.uiState.collect {}
        }

        advanceUntilIdle()
        viewModel.requestProgressReset()
        advanceUntilIdle()

        assertTrue(
            viewModel.uiState.value.showResetConfirmation
        )

        collectionJob.cancel()
    }

    @Test
    fun dismissingReset_hidesDialog() = runTest {
        val collectionJob = backgroundScope.launch(
            UnconfinedTestDispatcher(testScheduler)
        ) {
            viewModel.uiState.collect {}
        }

        advanceUntilIdle()
        viewModel.requestProgressReset()
        viewModel.dismissResetConfirmation()
        advanceUntilIdle()

        assertFalse(
            viewModel.uiState.value.showResetConfirmation
        )

        collectionJob.cancel()
    }

    @Test
    fun confirmedReset_deletesSavedProgress() = runTest {
        progressRepository.addProgress(
            topicId = "variables",
            score = 3,
            totalQuestions = 4
        )

        val collectionJob = backgroundScope.launch(
            UnconfinedTestDispatcher(testScheduler)
        ) {
            viewModel.uiState.collect {}
        }

        advanceUntilIdle()

        viewModel.requestProgressReset()
        viewModel.confirmProgressReset()
        advanceUntilIdle()

        assertTrue(
            progressRepository.currentProgress().isEmpty()
        )

        collectionJob.cancel()
    }

    @Test
    fun disablingSoundEffects_updatesUiState() = runTest {
        val collectionJob = backgroundScope.launch(
            UnconfinedTestDispatcher(testScheduler)
        ) {
            viewModel.uiState.collect {}
        }

        viewModel.setSoundEffectsEnabled(false)
        advanceUntilIdle()

        assertFalse(
            viewModel.uiState.value.soundEffectsEnabled
        )

        collectionJob.cancel()
    }
}