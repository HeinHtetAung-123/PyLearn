package com.example.pylearn.testing

import com.example.pylearn.data.QuizProgressRepository
import com.example.pylearn.data.local.QuizProgressEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeQuizProgressRepository : QuizProgressRepository {

    private val progress =
        MutableStateFlow<List<QuizProgressEntity>>(emptyList())

    var saveCallCount: Int = 0
        private set

    override fun observeAllProgress(): Flow<List<QuizProgressEntity>> {
        return progress
    }

    override fun observeProgressForTopic(
        topicId: String
    ): Flow<QuizProgressEntity?> {
        return progress.map { progressRecords ->
            progressRecords.find { record ->
                record.topicId == topicId
            }
        }
    }

    override suspend fun saveQuizResult(
        topicId: String,
        score: Int,
        totalQuestions: Int
    ) {
        saveCallCount++

        val existingProgress =
            progress.value.find { record ->
                record.topicId == topicId
            }

        val updatedProgress = QuizProgressEntity(
            topicId = topicId,
            bestScore = maxOf(
                score,
                existingProgress?.bestScore ?: 0
            ),
            totalQuestions = totalQuestions,
            attemptCount =
                (existingProgress?.attemptCount ?: 0) + 1,
            isCompleted = true,
            lastAttemptTimestamp = 0L
        )

        progress.value =
            progress.value
                .filterNot { record ->
                    record.topicId == topicId
                } + updatedProgress
    }

    override suspend fun deleteAllProgress() {
        progress.value = emptyList()
    }

    suspend fun addProgress(
        topicId: String,
        score: Int,
        totalQuestions: Int
    ) {
        saveQuizResult(
            topicId = topicId,
            score = score,
            totalQuestions = totalQuestions
        )
    }

    fun currentProgress(): List<QuizProgressEntity> {
        return progress.value
    }



}