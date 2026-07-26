package com.example.pylearn

import android.app.Application
import com.example.pylearn.di.AppContainer

class PyLearnApplication : Application() {

    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()

        appContainer = AppContainer(
            context = this
        )
    }
}