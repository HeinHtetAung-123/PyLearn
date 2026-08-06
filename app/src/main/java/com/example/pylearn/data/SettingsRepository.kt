package com.example.pylearn.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.pylearn.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore by preferencesDataStore(
    name = "user_settings"
)

interface SettingsRepository {

    val userPreferences: Flow<UserPreferences>

    suspend fun setDarkModeEnabled(enabled: Boolean)

    suspend fun setLargeTextEnabled(enabled: Boolean)

    suspend fun setConfirmBeforeReset(enabled: Boolean)


    suspend fun setSoundEffectsEnabled(enabled: Boolean)
}

class DataStoreSettingsRepository(
    private val context: Context
) : SettingsRepository {

    private object PreferenceKeys {
        val darkModeEnabled =
            booleanPreferencesKey("dark_mode_enabled")

        val largeTextEnabled =
            booleanPreferencesKey("large_text_enabled")

        val confirmBeforeReset =
            booleanPreferencesKey("confirm_before_reset")

        val soundEffectsEnabled =
            booleanPreferencesKey("sound_effects_enabled")
    }

    override val userPreferences: Flow<UserPreferences> =
        context.settingsDataStore.data.map { preferences ->
            UserPreferences(
                darkModeEnabled =
                    preferences[PreferenceKeys.darkModeEnabled] ?: false,
                largeTextEnabled =
                    preferences[PreferenceKeys.largeTextEnabled] ?: false,
                confirmBeforeReset =
                    preferences[PreferenceKeys.confirmBeforeReset] ?: true,
                soundEffectsEnabled =
                    preferences[PreferenceKeys.soundEffectsEnabled] ?: true
            )
        }

    override suspend fun setDarkModeEnabled(enabled: Boolean) {
        context.settingsDataStore.edit { preferences ->
            preferences[PreferenceKeys.darkModeEnabled] = enabled
        }
    }

    override suspend fun setLargeTextEnabled(enabled: Boolean) {
        context.settingsDataStore.edit { preferences ->
            preferences[PreferenceKeys.largeTextEnabled] = enabled
        }
    }

    override suspend fun setConfirmBeforeReset(enabled: Boolean) {
        context.settingsDataStore.edit { preferences ->
            preferences[PreferenceKeys.confirmBeforeReset] = enabled
        }
    }

    override suspend fun setSoundEffectsEnabled(
        enabled: Boolean
    ) {
        context.settingsDataStore.edit { preferences ->
            preferences[PreferenceKeys.soundEffectsEnabled] = enabled
        }
    }
}