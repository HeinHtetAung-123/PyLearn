package com.example.pylearn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pylearn.data.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    settingsRepository: SettingsRepository
) : ViewModel() {

    val uiState = settingsRepository.userPreferences
        .map { preferences ->
            MainUiState(
                darkModeEnabled = preferences.darkModeEnabled,
                largeTextEnabled = preferences.largeTextEnabled,
                isLoading = false
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(
                stopTimeoutMillis = 5_000
            ),
            initialValue = MainUiState()
        )
}