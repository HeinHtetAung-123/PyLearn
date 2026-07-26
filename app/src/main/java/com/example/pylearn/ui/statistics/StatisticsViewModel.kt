package com.example.pylearn.ui.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pylearn.data.QuizProgressRepository
import com.example.pylearn.data.SampleLearningData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class StatisticsViewModel(
    quizProgressRepository: QuizProgressRepository
) : ViewModel() {

    val uiState = quizProgressRepository
        .observeAllProgress()
        .map { progressRecords ->

            val topicStatistics = progressRecords.mapNotNull { progress ->
                val topic = SampleLearningData.topics.find {
                    it.id == progress.topicId
                }

                topic?.let {
                    TopicStatistics(
                        topicId = progress.topicId,
                        topicTitle = it.title,
                        bestScore = progress.bestScore,
                        totalQuestions = progress.totalQuestions,
                        attemptCount = progress.attemptCount
                    )
                }
            }

            val completedTopicCount =
                progressRecords.count { it.isCompleted }

            val totalAttempts =
                progressRecords.sumOf { it.attemptCount }

            val averageBestScorePercentage =
                if (topicStatistics.isEmpty()) {
                    0
                } else {
                    topicStatistics
                        .map { it.bestScorePercentage }
                        .average()
                        .toInt()
                }

            StatisticsUiState(
                topicStatistics = topicStatistics,
                completedTopicCount = completedTopicCount,
                totalTopicCount = SampleLearningData.topics.size,
                totalAttempts = totalAttempts,
                averageBestScorePercentage =
                    averageBestScorePercentage,
                isLoading = false
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(
                stopTimeoutMillis = 5_000
            ),
            initialValue = StatisticsUiState(
                totalTopicCount = SampleLearningData.topics.size
            )
        )
}