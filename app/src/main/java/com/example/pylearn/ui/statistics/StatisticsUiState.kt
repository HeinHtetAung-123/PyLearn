package com.example.pylearn.ui.statistics

data class TopicStatistics(
    val topicId: String,
    val topicTitle: String,
    val bestScore: Int,
    val totalQuestions: Int,
    val attemptCount: Int
) {
    val bestScorePercentage: Int
        get() = if (totalQuestions == 0) {
            0
        } else {
            (bestScore * 100) / totalQuestions
        }
}

data class StatisticsUiState(
    val topicStatistics: List<TopicStatistics> = emptyList(),
    val completedTopicCount: Int = 0,
    val totalTopicCount: Int = 0,
    val totalAttempts: Int = 0,
    val averageBestScorePercentage: Int = 0,
    val isLoading: Boolean = true
) {
    val overallProgress: Float
        get() = if (totalTopicCount == 0) {
            0f
        } else {
            completedTopicCount.toFloat() / totalTopicCount.toFloat()
        }

    val overallProgressPercentage: Int
        get() = (overallProgress * 100).toInt()
}