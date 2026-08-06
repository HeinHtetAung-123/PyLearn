package com.example.pylearn.domain.model

data class UserPreferences(
    val darkModeEnabled: Boolean = false,
    val largeTextEnabled: Boolean = false,
    val confirmBeforeReset: Boolean = true,
    val soundEffectsEnabled: Boolean = true
)