package com.example.pylearn.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pylearn.data.QuizProgressRepository
import com.example.pylearn.data.SampleLearningData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ActivityViewModel(
    private val quizProgressRepository: QuizProgressRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(ActivityUiState())

    val uiState: StateFlow<ActivityUiState> =
        _uiState.asStateFlow()

    private val _events =
        MutableSharedFlow<ActivityUiEvent>(
            extraBufferCapacity = 1
        )

    val events: SharedFlow<ActivityUiEvent> =
        _events.asSharedFlow()

    fun loadTopic(topicId: String) {
        val selectedTopic =
            SampleLearningData.topics.find { topic ->
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
        val currentState = _uiState.value

        if (
            currentState.isAnswerSubmitted ||
            currentState.isQuizComplete
        ) {
            return
        }

        val currentQuestion =
            currentState.currentQuestion ?: return

        if (answerIndex !in currentQuestion.options.indices) {
            return
        }

        _uiState.update {
            it.copy(
                selectedAnswerIndex = answerIndex
            )
        }
    }

    fun submitAnswer() {
        val currentState = _uiState.value

        if (
            currentState.selectedAnswerIndex == null ||
            currentState.isAnswerSubmitted ||
            currentState.isQuizComplete
        ) {
            return
        }

        val currentQuestion =
            currentState.currentQuestion ?: return

        val isCorrect =
            currentState.selectedAnswerIndex ==
                    currentQuestion.correctAnswerIndex

        _uiState.value = currentState.copy(
            isAnswerSubmitted = true,
            score = if (isCorrect) {
                currentState.score + 1
            } else {
                currentState.score
            }
        )

        if (isCorrect) {
            _events.tryEmit(
                ActivityUiEvent.CorrectAnswer
            )
        } else {
            _events.tryEmit(
                ActivityUiEvent.IncorrectAnswer
            )
        }
    }

    fun moveToNextQuestion() {
        val currentState = _uiState.value

        if (
            !currentState.isAnswerSubmitted ||
            currentState.isQuizComplete
        ) {
            return
        }

        val isLastQuestion =
            currentState.currentQuestionIndex ==
                    currentState.questions.lastIndex

        if (isLastQuestion) {
            _uiState.update {
                it.copy(
                    isQuizComplete = true
                )
            }

            _events.tryEmit(
                ActivityUiEvent.ActivityCompleted
            )

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

        val topicId =
            currentState.topic?.id ?: return

        if (currentState.isResultSaved) {
            return
        }

        _uiState.update {
            it.copy(
                isResultSaved = true
            )
        }

        viewModelScope.launch {
            quizProgressRepository.saveQuizResult(
                topicId = topicId,
                score = currentState.score,
                totalQuestions =
                    currentState.totalQuestions
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
                isQuizComplete = false,
                isResultSaved = false
            )
        }
    }
}