package com.example.pylearn.ui.settings

data class SettingsUiState(
    val darkModeEnabled: Boolean = false,
    val largeTextEnabled: Boolean = false,
    val confirmBeforeReset: Boolean = true,
    val soundEffectsEnabled: Boolean = true,
    val showResetConfirmation: Boolean = false,
    val isLoading: Boolean = true
)