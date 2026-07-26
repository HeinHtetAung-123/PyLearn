package com.example.pylearn.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pylearn.data.QuizProgressRepository
import com.example.pylearn.data.SampleLearningData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ActivityViewModel(
    private val quizProgressRepository: QuizProgressRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ActivityUiState())
    val uiState: StateFlow<ActivityUiState> = _uiState.asStateFlow()

    fun loadTopic(topicId: String) {
        val selectedTopic = SampleLearningData.topics.find { topic ->
            topic.id == topicId
        }

        val topicQuestions =
            SampleLearningData.getQuestionsForTopic(topicId)

        _uiState.value = when {
            selectedTopic == null -> {
                ActivityUiState(
                    errorMessage =
                        "The selected Python topic could not be found."
                )
            }

            topicQuestions.isEmpty() -> {
                ActivityUiState(
                    topic = selectedTopic,
                    errorMessage =
                        "No questions are currently available for this topic."
                )
            }

            else -> {
                ActivityUiState(
                    topic = selectedTopic,
                    questions = topicQuestions
                )
            }
        }
    }

    fun selectAnswer(answerIndex: Int) {
        if (_uiState.value.isAnswerSubmitted) {
            return
        }

        _uiState.update { currentState ->
            currentState.copy(
                selectedAnswerIndex = answerIndex
            )
        }
    }

    fun submitAnswer() {
        val currentState = _uiState.value
        val question = currentState.currentQuestion
        val selectedIndex = currentState.selectedAnswerIndex

        if (
            question == null ||
            selectedIndex == null ||
            currentState.isAnswerSubmitted
        ) {
            return
        }

        val isCorrect =
            selectedIndex == question.correctAnswerIndex

        _uiState.update {
            it.copy(
                score = if (isCorrect) {
                    it.score + 1
                } else {
                    it.score
                },
                isAnswerSubmitted = true
            )
        }
    }

    fun moveToNextQuestion() {
        val currentState = _uiState.value

        if (!currentState.isAnswerSubmitted) {
            return
        }

        val isLastQuestion =
            currentState.currentQuestionIndex ==
                    currentState.questions.lastIndex

        if (isLastQuestion) {
            _uiState.update {
                it.copy(isQuizComplete = true)
            }

            saveCompletedQuiz()
        } else {
            _uiState.update {
                it.copy(
                    currentQuestionIndex =
                        it.currentQuestionIndex + 1,
                    selectedAnswerIndex = null,
                    isAnswerSubmitted = false
                )
            }
        }
    }

    private fun saveCompletedQuiz() {
        val currentState = _uiState.value
        val topicId = currentState.topic?.id ?: return

        viewModelScope.launch {
            quizProgressRepository.saveQuizResult(
                topicId = topicId,
                score = currentState.score,
                totalQuestions = currentState.totalQuestions
            )
        }
    }

    fun restartQuiz() {
        _uiState.update {
            it.copy(
                currentQuestionIndex = 0,
                selectedAnswerIndex = null,
                isAnswerSubmitted = false,
                score = 0,
                isQuizComplete = false
            )
        }
    }
}