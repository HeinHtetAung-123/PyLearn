package com.example.pylearn.ui.flashcards

import com.example.pylearn.domain.model.Flashcard
import com.example.pylearn.domain.model.PythonTopic

data class FlashcardUiState(
    val topic: PythonTopic? = null,
    val flashcards: List<Flashcard> = emptyList(),
    val currentCardIndex: Int = 0,
    val isCardFlipped: Boolean = false,
    val rememberedCount: Int = 0,
    val reviewAgainCount: Int = 0,
    val isSessionComplete: Boolean = false,
    val errorMessage: String? = null
) {
    val currentCard: Flashcard?
        get() = flashcards.getOrNull(currentCardIndex)

    val totalCards: Int
        get() = flashcards.size

    val currentCardNumber: Int
        get() = if (flashcards.isEmpty()) {
            0
        } else {
            currentCardIndex + 1
        }

    val progress: Float
        get() = if (flashcards.isEmpty()) {
            0f
        } else {
            currentCardNumber.toFloat() /
                    flashcards.size.toFloat()
        }
}