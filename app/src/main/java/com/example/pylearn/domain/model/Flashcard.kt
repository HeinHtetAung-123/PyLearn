package com.example.pylearn.domain.model

data class Flashcard(
    val id: String,
    val topicId: String,
    val prompt: String,
    val answer: String,
    val codeSnippet: String? = null,
    val explanation: String? = null
)