package com.example.pylearn.ui.activity

import com.example.pylearn.domain.model.PythonTopic

data class ActivityUiState(
    val topic: PythonTopic? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)