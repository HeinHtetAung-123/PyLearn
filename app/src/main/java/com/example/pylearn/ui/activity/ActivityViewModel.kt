package com.example.pylearn.ui.activity

import androidx.lifecycle.ViewModel
import com.example.pylearn.data.SampleLearningData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ActivityViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ActivityUiState())
    val uiState: StateFlow<ActivityUiState> = _uiState.asStateFlow()

    fun loadTopic(topicId: String) {
        val selectedTopic = SampleLearningData.topics.find { topic ->
            topic.id == topicId
        }

        _uiState.value = if (selectedTopic != null) {
            ActivityUiState(topic = selectedTopic)
        } else {
            ActivityUiState(
                errorMessage = "The selected Python topic could not be found."
            )
        }
    }
}