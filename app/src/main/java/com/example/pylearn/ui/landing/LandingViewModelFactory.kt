package com.example.pylearn.ui.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pylearn.data.QuizProgressRepository

class LandingViewModelFactory(
    private val quizProgressRepository: QuizProgressRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (
            modelClass.isAssignableFrom(
                LandingViewModel::class.java
            )
        ) {
            return LandingViewModel(
                quizProgressRepository =
                    quizProgressRepository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}