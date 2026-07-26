package com.example.pylearn.di

import android.content.Context
import androidx.room.Room
import com.example.pylearn.data.DataStoreSettingsRepository
import com.example.pylearn.data.QuizProgressRepository
import com.example.pylearn.data.RoomQuizProgressRepository
import com.example.pylearn.data.SettingsRepository
import com.example.pylearn.data.local.PyLearnDatabase

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
}