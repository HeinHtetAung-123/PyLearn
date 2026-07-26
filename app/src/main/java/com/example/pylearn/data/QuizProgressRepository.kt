package com.example.pylearn.data

import com.example.pylearn.data.local.QuizProgressDao
import com.example.pylearn.data.local.QuizProgressEntity
import kotlinx.coroutines.flow.Flow

interface QuizProgressRepository {

    fun observeAllProgress(): Flow<List<QuizProgressEntity>>

    fun observeProgressForTopic(
        topicId: String
    ): Flow<QuizProgressEntity?>

    suspend fun saveQuizResult(
        topicId: String,
        score: Int,
        totalQuestions: Int
    )

    suspend fun deleteAllProgress()
}

class RoomQuizProgressRepository(
    private val quizProgressDao: QuizProgressDao
) : QuizProgressRepository {

    override fun observeAllProgress(): Flow<List<QuizProgressEntity>> {
        return quizProgressDao.observeAllProgress()
    }

    override fun observeProgressForTopic(
        topicId: String
    ): Flow<QuizProgressEntity?> {
        return quizProgressDao.observeProgressForTopic(topicId)
    }

    override suspend fun saveQuizResult(
        topicId: String,
        score: Int,
        totalQuestions: Int
    ) {
        val existingProgress =
            quizProgressDao.getProgressForTopic(topicId)

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
            lastAttemptTimestamp = System.currentTimeMillis()
        )

        quizProgressDao.upsertProgress(updatedProgress)
    }

    override suspend fun deleteAllProgress() {
        quizProgressDao.deleteAllProgress()
    }
}