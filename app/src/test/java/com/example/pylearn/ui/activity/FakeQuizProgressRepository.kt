package com.example.pylearn.ui.activity

import com.example.pylearn.data.QuizProgressRepository
import com.example.pylearn.data.local.QuizProgressEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeQuizProgressRepository :
    QuizProgressRepository {

    private val progress =
        MutableStateFlow<List<QuizProgressEntity>>(emptyList())

    override fun observeAllProgress():
            Flow<List<QuizProgressEntity>> {
        return progress
    }

    override fun observeProgressForTopic(
        topicId: String
    ): Flow<QuizProgressEntity?> {
        return MutableStateFlow(
            progress.value.find { it.topicId == topicId }
        )
    }

    override suspend fun saveQuizResult(
        topicId: String,
        score: Int,
        totalQuestions: Int
    ) {
        val existing =
            progress.value.find { it.topicId == topicId }

        val updated = QuizProgressEntity(
            topicId = topicId,
            bestScore = maxOf(
                score,
                existing?.bestScore ?: 0
            ),
            totalQuestions = totalQuestions,
            attemptCount =
                (existing?.attemptCount ?: 0) + 1,
            isCompleted = true,
            lastAttemptTimestamp = 0L
        )

        progress.value =
            progress.value
                .filterNot { it.topicId == topicId } +
                    updated
    }

    override suspend fun deleteAllProgress() {
        progress.value = emptyList()
    }
}