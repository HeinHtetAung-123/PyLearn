package com.example.pylearn.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pylearn.data.QuizProgressRepository

class ActivityViewModelFactory(
    private val quizProgressRepository: QuizProgressRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (
            modelClass.isAssignableFrom(
                ActivityViewModel::class.java
            )
        ) {
            return ActivityViewModel(
                quizProgressRepository =
                    quizProgressRepository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}