package com.example.pylearn.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_progress")
data class QuizProgressEntity(
    @PrimaryKey
    val topicId: String,
    val bestScore: Int,
    val totalQuestions: Int,
    val attemptCount: Int,
    val isCompleted: Boolean,
    val lastAttemptTimestamp: Long
)