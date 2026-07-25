package com.example.pylearn.ui.landing

import com.example.pylearn.domain.model.PythonTopic

data class LandingUiState(
    val topics: List<PythonTopic> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)