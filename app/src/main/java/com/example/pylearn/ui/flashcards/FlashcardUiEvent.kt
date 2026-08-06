package com.example.pylearn.ui.flashcards

sealed interface FlashcardUiEvent {

    data object CardFlipped : FlashcardUiEvent

    data object SessionCompleted : FlashcardUiEvent
}