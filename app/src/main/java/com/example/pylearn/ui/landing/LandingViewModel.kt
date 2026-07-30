package com.example.pylearn.ui.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pylearn.data.QuizProgressRepository
import com.example.pylearn.data.SampleLearningData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class LandingViewModel(
    quizProgressRepository: QuizProgressRepository
) : ViewModel() {

    val uiState = quizProgressRepository
        .observeAllProgress()
        .map { progressRecords ->
            val progressByTopicId =
                progressRecords.associate { progress ->
                    progress.topicId to TopicProgressSummary(
                        bestScore = progress.bestScore,
                        totalQuestions = progress.totalQuestions,
                        attemptCount = progress.attemptCount,
                        isCompleted = progress.isCompleted
                    )
                }

            LandingUiState(
                topics = SampleLearningData.topics,
                progressByTopicId = progressByTopicId,
                isLoading = false
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(
                stopTimeoutMillis = 5_000
            ),
            initialValue = LandingUiState(
                topics = SampleLearningData.topics
            )
        )
}