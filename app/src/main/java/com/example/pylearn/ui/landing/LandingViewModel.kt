package com.example.pylearn.ui.landing

import androidx.lifecycle.ViewModel
import com.example.pylearn.data.SampleLearningData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LandingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        LandingUiState(
            topics = SampleLearningData.topics
        )
    )

    val uiState: StateFlow<LandingUiState> = _uiState.asStateFlow()
}