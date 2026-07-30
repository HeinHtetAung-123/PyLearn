package com.example.pylearn.data.remote

import com.google.gson.annotations.SerializedName

data class CodeExecutionResponse(
    val stdout: String? = null,

    val stderr: String? = null,

    @SerializedName("compile_output")
    val compileOutput: String? = null,

    val message: String? = null,

    val status: ExecutionStatus,

    val time: String? = null,

    val memory: Int? = null
)