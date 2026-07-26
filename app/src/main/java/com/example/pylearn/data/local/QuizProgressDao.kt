package com.example.pylearn.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizProgressDao {

    @Upsert
    suspend fun upsertProgress(progress: QuizProgressEntity)

    @Query("SELECT * FROM quiz_progress ORDER BY lastAttemptTimestamp DESC")
    fun observeAllProgress(): Flow<List<QuizProgressEntity>>

    @Query("SELECT * FROM quiz_progress WHERE topicId = :topicId LIMIT 1")
    fun observeProgressForTopic(topicId: String): Flow<QuizProgressEntity?>

    @Query("SELECT * FROM quiz_progress WHERE topicId = :topicId LIMIT 1")
    suspend fun getProgressForTopic(topicId: String): QuizProgressEntity?

    @Query("DELETE FROM quiz_progress")
    suspend fun deleteAllProgress()
}