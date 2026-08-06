package com.example.pylearn.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pylearn.data.QuizProgressRepository
import com.example.pylearn.data.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
    private val quizProgressRepository: QuizProgressRepository
) : ViewModel() {

    private val showResetConfirmation =
        MutableStateFlow(false)

    val uiState = combine(
        settingsRepository.userPreferences,
        showResetConfirmation
    ) { preferences, showDialog ->
        SettingsUiState(
            darkModeEnabled = preferences.darkModeEnabled,
            largeTextEnabled = preferences.largeTextEnabled,
            confirmBeforeReset = preferences.confirmBeforeReset,
            soundEffectsEnabled = preferences.soundEffectsEnabled,
            showResetConfirmation = showDialog,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(
            stopTimeoutMillis = 5_000
        ),
        initialValue = SettingsUiState()
    )

    fun setDarkModeEnabled(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setDarkModeEnabled(enabled)
        }
    }

    fun setLargeTextEnabled(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setLargeTextEnabled(enabled)
        }
    }

    fun setConfirmBeforeReset(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setConfirmBeforeReset(enabled)
        }
    }

    fun requestProgressReset() {
        if (uiState.value.confirmBeforeReset) {
            showResetConfirmation.value = true
        } else {
            resetProgress()
        }
    }

    fun dismissResetConfirmation() {
        showResetConfirmation.value = false
    }

    fun confirmProgressReset() {
        showResetConfirmation.value = false
        resetProgress()
    }

    private fun resetProgress() {
        viewModelScope.launch {
            quizProgressRepository.deleteAllProgress()
        }
    }

    fun setSoundEffectsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setSoundEffectsEnabled(enabled)
        }
    }
}