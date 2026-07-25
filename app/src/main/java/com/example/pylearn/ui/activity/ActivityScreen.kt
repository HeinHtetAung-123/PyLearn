package com.example.pylearn.ui.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ActivityScreen(
    uiState: ActivityUiState,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Learning Activity",
            style = MaterialTheme.typography.headlineMedium
        )

        when {
            uiState.isLoading -> {
                Text(text = "Loading topic...")
            }

            uiState.errorMessage != null -> {
                Text(
                    text = uiState.errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }

            uiState.topic != null -> {
                val topic = uiState.topic

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = topic.title,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text(
                            text = topic.description,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Text(
                            text = "Learning objective",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = topic.learningObjective,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Text(
                            text = "Difficulty: ${
                                topic.difficulty.name
                                    .lowercase()
                                    .replaceFirstChar { it.uppercase() }
                            }",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }

                Button(
                    onClick = {
                        // Real quiz logic will be added next.
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Begin Activity")
                }
            }
        }

        Button(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Back to Home")
        }
    }
}