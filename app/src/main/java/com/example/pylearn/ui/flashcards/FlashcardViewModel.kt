package com.example.pylearn.ui.flashcards

import androidx.lifecycle.ViewModel
import com.example.pylearn.data.SampleFlashcardData
import com.example.pylearn.data.SampleLearningData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FlashcardViewModel : ViewModel() {

    private val _uiState =
        MutableStateFlow(FlashcardUiState())

    val uiState: StateFlow<FlashcardUiState> =
        _uiState.asStateFlow()

    private val _events =
        MutableSharedFlow<FlashcardUiEvent>(
            extraBufferCapacity = 1
        )

    val events: SharedFlow<FlashcardUiEvent> =
        _events.asSharedFlow()

    fun loadTopic(topicId: String) {
        val topic =
            SampleLearningData.topics.find {
                it.id == topicId
            }

        val flashcards =
            SampleFlashcardData.getFlashcardsForTopic(
                topicId
            )

        _uiState.value = when {
            topic == null -> {
                FlashcardUiState(
                    errorMessage =
                        "The selected Python topic could not be found."
                )
            }

            flashcards.isEmpty() -> {
                FlashcardUiState(
                    topic = topic,
                    errorMessage =
                        "No flashcards are available for this topic."
                )
            }

            else -> {
                FlashcardUiState(
                    topic = topic,
                    flashcards = flashcards
                )
            }
        }
    }

    fun flipCard() {
        val currentState = _uiState.value

        if (
            currentState.currentCard == null ||
            currentState.isSessionComplete
        ) {
            return
        }

        _uiState.update {
            it.copy(
                isCardFlipped = !it.isCardFlipped
            )
        }

        _events.tryEmit(
            FlashcardUiEvent.CardFlipped
        )
    }

    fun markRemembered() {
        moveToNextCard(
            rememberedIncrement = 1,
            reviewAgainIncrement = 0
        )
    }

    fun markForReview() {
        moveToNextCard(
            rememberedIncrement = 0,
            reviewAgainIncrement = 1
        )
    }

    private fun moveToNextCard(
        rememberedIncrement: Int,
        reviewAgainIncrement: Int
    ) {
        val currentState = _uiState.value

        if (
            !currentState.isCardFlipped ||
            currentState.currentCard == null ||
            currentState.isSessionComplete
        ) {
            return
        }

        val isLastCard =
            currentState.currentCardIndex ==
                    currentState.flashcards.lastIndex

        if (isLastCard) {
            _uiState.update {
                it.copy(
                    rememberedCount =
                        it.rememberedCount +
                                rememberedIncrement,
                    reviewAgainCount =
                        it.reviewAgainCount +
                                reviewAgainIncrement,
                    isSessionComplete = true
                )
            }

            _events.tryEmit(
                FlashcardUiEvent.SessionCompleted
            )
        } else {
            _uiState.update {
                it.copy(
                    currentCardIndex =
                        it.currentCardIndex + 1,
                    isCardFlipped = false,
                    rememberedCount =
                        it.rememberedCount +
                                rememberedIncrement,
                    reviewAgainCount =
                        it.reviewAgainCount +
                                reviewAgainIncrement
                )
            }
        }
    }

    fun restartSession() {
        _uiState.update {
            it.copy(
                currentCardIndex = 0,
                isCardFlipped = false,
                rememberedCount = 0,
                reviewAgainCount = 0,
                isSessionComplete = false
            )
        }
    }
}