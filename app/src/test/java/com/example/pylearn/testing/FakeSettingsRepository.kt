package com.example.pylearn.testing

import com.example.pylearn.data.SettingsRepository
import com.example.pylearn.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeSettingsRepository : SettingsRepository {

    private val preferences =
        MutableStateFlow(UserPreferences())

    override val userPreferences: Flow<UserPreferences> =
        preferences

    override suspend fun setDarkModeEnabled(enabled: Boolean) {
        preferences.value =
            preferences.value.copy(
                darkModeEnabled = enabled
            )
    }

    override suspend fun setLargeTextEnabled(enabled: Boolean) {
        preferences.value =
            preferences.value.copy(
                largeTextEnabled = enabled
            )
    }

    override suspend fun setConfirmBeforeReset(enabled: Boolean) {
        preferences.value =
            preferences.value.copy(
                confirmBeforeReset = enabled
            )
    }
}