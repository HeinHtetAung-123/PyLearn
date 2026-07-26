package com.example.pylearn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pylearn.data.SettingsRepository

class MainViewModelFactory(
    private val settingsRepository: SettingsRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                settingsRepository = settingsRepository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}