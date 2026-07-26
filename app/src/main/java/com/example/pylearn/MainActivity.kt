package com.example.pylearn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.pylearn.ui.theme.PyLearnTheme
import com.example.pylearn.navigation.PyLearnNavHost

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            settingsRepository =
                (application as PyLearnApplication)
                    .appContainer
                    .settingsRepository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val uiState by
            mainViewModel.uiState.collectAsStateWithLifecycle()

            PyLearnTheme(
                darkTheme = uiState.darkModeEnabled,
                largeTextEnabled = uiState.largeTextEnabled
            ) {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    PyLearnNavHost(
                        navController = navController
                    )
                }
            }
        }
    }
}