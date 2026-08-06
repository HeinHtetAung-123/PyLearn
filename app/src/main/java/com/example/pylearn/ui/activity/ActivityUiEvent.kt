package com.example.pylearn.ui.activity

sealed interface ActivityUiEvent {

    data object CorrectAnswer : ActivityUiEvent

    data object IncorrectAnswer : ActivityUiEvent

    data object ActivityCompleted : ActivityUiEvent
}