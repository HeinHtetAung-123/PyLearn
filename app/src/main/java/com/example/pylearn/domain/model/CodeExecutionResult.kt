package com.example.pylearn.domain.model

data class CodeExecutionResult(
    val output: String,
    val statusDescription: String,
    val executionTime: String? = null,
    val memoryUsedKb: Int? = null,
    val isSuccessful: Boolean
)