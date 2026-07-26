package com.example.pylearn.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pylearn.data.QuizProgressRepository
import com.example.pylearn.data.SettingsRepository

class SettingsViewModelFactory(
    private val settingsRepository: SettingsRepository,
    private val quizProgressRepository: QuizProgressRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (
            modelClass.isAssignableFrom(
                SettingsViewModel::class.java
            )
        ) {
            return SettingsViewModel(
                settingsRepository = settingsRepository,
                quizProgressRepository = quizProgressRepository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}