package com.example.pylearn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.pylearn.navigation.PyLearnNavHost
import com.example.pylearn.ui.theme.PyLearnTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            PyLearnTheme {
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