package com.example.pylearn.ui.landing

import com.example.pylearn.domain.model.PythonTopic

data class TopicProgressSummary(
    val bestScore: Int,
    val totalQuestions: Int,
    val attemptCount: Int,
    val isCompleted: Boolean
) {
    val bestScorePercentage: Int
        get() = if (totalQuestions == 0) {
            0
        } else {
            (bestScore * 100) / totalQuestions
        }
}

data class LandingUiState(
    val topics: List<PythonTopic> = emptyList(),
    val progressByTopicId: Map<String, TopicProgressSummary> =
        emptyMap(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)