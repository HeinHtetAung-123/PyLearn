package com.example.pylearn.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [QuizProgressEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PyLearnDatabase : RoomDatabase() {

    abstract fun quizProgressDao(): QuizProgressDao
}