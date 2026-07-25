package com.example.pylearn.domain.model

data class LearningQuestion(
    val id: String,
    val topicId: String,
    val questionText: String,
    val codeSnippet: String? = null,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String,
    val questionType: QuestionType,
    val difficulty: DifficultyLevel
) {
    init {
        require(options.isNotEmpty()) {
            "A learning question must contain at least one answer option."
        }

        require(correctAnswerIndex in options.indices) {
            "The correct answer index must match one of the available options."
        }
    }
}