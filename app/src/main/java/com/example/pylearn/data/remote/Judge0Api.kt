package com.example.pylearn.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Judge0Api {

    @POST("submissions")
    suspend fun createSubmission(
        @Query("base64_encoded")
        base64Encoded: Boolean = false,

        @Query("wait")
        wait: Boolean = false,

        @Body
        request: CodeSubmissionRequest
    ): SubmissionTokenResponse

    @GET("submissions/{token}")
    suspend fun getSubmission(
        @Path("token")
        token: String,

        @Query("base64_encoded")
        base64Encoded: Boolean = false,

        @Query("fields")
        fields: String =
            "stdout,stderr,compile_output,message,status,time,memory"
    ): CodeExecutionResponse
}