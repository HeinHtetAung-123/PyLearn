package com.example.pylearn.di

import android.content.Context
import androidx.room.Room
import com.example.pylearn.data.CodeExecutionRepository
import com.example.pylearn.data.DataStoreSettingsRepository
import com.example.pylearn.data.Judge0CodeExecutionRepository
import com.example.pylearn.data.QuizProgressRepository
import com.example.pylearn.data.RoomQuizProgressRepository
import com.example.pylearn.data.SettingsRepository
import com.example.pylearn.data.local.PyLearnDatabase
import com.example.pylearn.data.remote.Judge0ApiClient
import com.example.pylearn.audio.AndroidSoundPlayer
import com.example.pylearn.audio.SoundPlayer

class AppContainer(
    context: Context
) {
    private val applicationContext =
        context.applicationContext

    private val database: PyLearnDatabase =
        Room.databaseBuilder(
            applicationContext,
            PyLearnDatabase::class.java,
            "pylearn_database"
        ).build()

    val quizProgressRepository: QuizProgressRepository =
        RoomQuizProgressRepository(
            quizProgressDao = database.quizProgressDao()
        )

    val settingsRepository: SettingsRepository =
        DataStoreSettingsRepository(
            context = applicationContext
        )

    val codeExecutionRepository: CodeExecutionRepository =
        Judge0CodeExecutionRepository(
            judge0Api = Judge0ApiClient.api
        )

    val soundPlayer: SoundPlayer =
        AndroidSoundPlayer(
            context = applicationContext
        )
}