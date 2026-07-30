package com.example.pylearn.ui.activity

import com.example.pylearn.domain.model.LearningQuestion
import com.example.pylearn.domain.model.PythonTopic

data class ActivityUiState(
    val topic: PythonTopic? = null,
    val questions: List<LearningQuestion> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedAnswerIndex: Int? = null,
    val isAnswerSubmitted: Boolean = false,
    val score: Int = 0,
    val isQuizComplete: Boolean = false,
    val isResultSaved: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val currentQuestion: LearningQuestion?
        get() = questions.getOrNull(currentQuestionIndex)

    val questionNumber: Int
        get() = if (questions.isEmpty()) 0 else currentQuestionIndex + 1

    val totalQuestions: Int
        get() = questions.size

    val progress: Float
        get() = if (questions.isEmpty()) {
            0f
        } else {
            questionNumber.toFloat() / totalQuestions.toFloat()
        }
}