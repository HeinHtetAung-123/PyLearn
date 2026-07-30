package com.example.pylearn.data.remote

import com.google.gson.annotations.SerializedName

data class CodeSubmissionRequest(
    @SerializedName("source_code")
    val sourceCode: String,

    @SerializedName("language_id")
    val languageId: Int,

    @SerializedName("stdin")
    val standardInput: String? = null
)