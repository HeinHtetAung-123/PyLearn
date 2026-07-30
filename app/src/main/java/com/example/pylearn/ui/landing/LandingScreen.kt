package com.example.pylearn.ui.landing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pylearn.domain.model.PythonTopic

@Composable
fun LandingScreen(
    uiState: LandingUiState,
    onTopicClick: (PythonTopic) -> Unit,
    onStatisticsClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onCodeRunnerClick: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "PyLearn",
                    style = MaterialTheme.typography.headlineLarge
                )

                Text(
                    text = "Build your Python skills through short lessons and interactive activities.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onStatisticsClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Statistics")
                }

                OutlinedButton(
                    onClick = onSettingsClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Settings")
                }
            }
        }

        item {
            Button(
                onClick = onCodeRunnerClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Open Python Code Runner")
            }
        }

        item {
            Text(
                text = "Python Topics",
                style = MaterialTheme.typography.titleLarge
            )
        }

        items(
            items = uiState.topics,
            key = { topic -> topic.id }
        ) { topic ->
            TopicCard(
                topic = topic,
                progress = uiState.progressByTopicId[topic.id],
                onClick = {
                    onTopicClick(topic)
                }
            )
        }
    }
}

@Composable
private fun TopicCard(
    topic: PythonTopic,
    progress: TopicProgressSummary?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = topic.title,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = topic.description,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Difficulty: ${topic.difficulty.name.lowercase().replaceFirstChar { it.uppercase() }}",
                style = MaterialTheme.typography.labelMedium
            )

            if (progress == null) {
                Text(
                    text = "Not started",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                Text(
                    text = if (progress.isCompleted) {
                        "Completed"
                    } else {
                        "In progress"
                    },
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text =
                        "Best score: ${progress.bestScore} of " +
                                "${progress.totalQuestions} " +
                                "(${progress.bestScorePercentage}%)",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Attempts: ${progress.attemptCount}",
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (progress == null) {
                        "Start Topic"
                    } else {
                        "Practice Again"
                    }
                )
            }
        }
    }
}