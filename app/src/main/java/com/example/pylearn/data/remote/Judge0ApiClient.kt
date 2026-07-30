package com.example.pylearn.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Judge0ApiClient {

    private const val BASE_URL =
        "https://ce.judge0.com/"

    val api: Judge0Api by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(Judge0Api::class.java)
    }
}