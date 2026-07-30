package com.example.pylearn.ui.coderunner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pylearn.data.CodeExecutionRepository

class CodeRunnerViewModelFactory(
    private val codeExecutionRepository: CodeExecutionRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (
            modelClass.isAssignableFrom(
                CodeRunnerViewModel::class.java
            )
        ) {
            return CodeRunnerViewModel(
                codeExecutionRepository = codeExecutionRepository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}