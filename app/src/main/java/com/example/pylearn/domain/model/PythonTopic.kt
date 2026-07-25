package com.example.pylearn.domain.model

data class PythonTopic(
    val id: String,
    val title: String,
    val description: String,
    val learningObjective: String,
    val difficulty: DifficultyLevel,
    val completedLessonCount: Int = 0,
    val totalLessonCount: Int = 1
)