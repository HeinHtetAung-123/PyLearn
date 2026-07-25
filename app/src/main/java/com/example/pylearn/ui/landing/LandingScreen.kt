package com.example.pylearn.ui.landing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LandingScreen(
    onActivityClick: () -> Unit,
    onStatisticsClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "PyLearn")

        Text(text = "Learn Python through interactive activities.")

        Button(onClick = onActivityClick) {
            Text(text = "Start Learning")
        }

        Button(onClick = onStatisticsClick) {
            Text(text = "View Statistics")
        }

        Button(onClick = onSettingsClick) {
            Text(text = "Settings")
        }
    }
}