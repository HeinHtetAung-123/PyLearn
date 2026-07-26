package com.example.pylearn.ui.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatisticsScreen(
    uiState: StatisticsUiState,
    onBackClick: () -> Unit
) {
    if (uiState.isLoading) {
        StatisticsLoadingScreen()
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "User Statistics",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        item {
            Text(
                text = "Review your progress and best quiz results.",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        item {
            OverallProgressCard(uiState = uiState)
        }

        item {
            StatisticsSummary(uiState = uiState)
        }

        item {
            Text(
                text = "Topic Results",
                style = MaterialTheme.typography.titleLarge
            )
        }

        if (uiState.topicStatistics.isEmpty()) {
            item {
                EmptyStatisticsCard()
            }
        } else {
            items(
                items = uiState.topicStatistics,
                key = { statistics -> statistics.topicId }
            ) { statistics ->
                TopicStatisticsCard(
                    statistics = statistics
                )
            }
        }

        item {
            OutlinedButton(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Back to Home")
            }
        }
    }
}

@Composable
private fun OverallProgressCard(
    uiState: StatisticsUiState
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Overall Progress",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "${uiState.overallProgressPercentage}% completed",
                style = MaterialTheme.typography.headlineSmall
            )

            LinearProgressIndicator(
                progress = {
                    uiState.overallProgress
                },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text =
                    "${uiState.completedTopicCount} of " +
                            "${uiState.totalTopicCount} topics completed",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun StatisticsSummary(
    uiState: StatisticsUiState
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SummaryCard(
            title = "Attempts",
            value = uiState.totalAttempts.toString(),
            modifier = Modifier.weight(1f)
        )

        SummaryCard(
            title = "Average Best",
            value = "${uiState.averageBestScorePercentage}%",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun SummaryCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
private fun TopicStatisticsCard(
    statistics: TopicStatistics
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = statistics.topicTitle,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text =
                    "Best score: ${statistics.bestScore} out of " +
                            "${statistics.totalQuestions}",
                style = MaterialTheme.typography.bodyMedium
            )

            LinearProgressIndicator(
                progress = {
                    statistics.bestScorePercentage / 100f
                },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text =
                    "Best result: " +
                            "${statistics.bestScorePercentage}%",
                style = MaterialTheme.typography.labelLarge
            )

            Text(
                text =
                    "Attempts: ${statistics.attemptCount}",
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
private fun EmptyStatisticsCard() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "No quiz results yet",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text =
                    "Complete a Python topic activity to begin " +
                            "building your statistics.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun StatisticsLoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement =
            Arrangement.Center,
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}