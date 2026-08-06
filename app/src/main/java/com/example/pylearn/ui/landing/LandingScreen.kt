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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment

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
    val isCompleted = progress?.isCompleted == true
    val progressValue =
        (progress?.bestScorePercentage ?: 0) / 100f

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = topic.title,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = topic.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color =
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                if (isCompleted) {
                    Surface(
                        shape = CircleShape,
                        color =
                            MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = "✓",
                            modifier = Modifier.padding(
                                horizontal = 10.dp,
                                vertical = 6.dp
                            ),
                            style =
                                MaterialTheme.typography.titleMedium,
                            color =
                                MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DifficultyChip(
                    difficulty = topic.difficulty.name
                        .lowercase()
                        .replaceFirstChar { it.uppercase() }
                )

                Text(
                    text = when {
                        progress == null ->
                            "Not started"

                        isCompleted ->
                            "${progress.bestScorePercentage}%"

                        else ->
                            "In progress"
                    },
                    style = MaterialTheme.typography.labelLarge,
                    color = if (isCompleted) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }

            if (progress != null) {
                LinearProgressIndicator(
                    progress = {
                        progressValue.coerceIn(0f, 1f)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CompactStat(
                        label = "Best",
                        value =
                            "${progress.bestScore}/${progress.totalQuestions}"
                    )

                    CompactStat(
                        label = "Attempts",
                        value = progress.attemptCount.toString()
                    )
                }
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

@Composable
private fun DifficultyChip(
    difficulty: String
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color =
            MaterialTheme.colorScheme.secondaryContainer
    ) {
        Text(
            text = difficulty,
            modifier = Modifier.padding(
                horizontal = 10.dp,
                vertical = 5.dp
            ),
            style = MaterialTheme.typography.labelMedium,
            color =
                MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Composable
private fun CompactStat(
    label: String,
    value: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color =
                MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = value,
            style = MaterialTheme.typography.labelLarge
        )
    }
}