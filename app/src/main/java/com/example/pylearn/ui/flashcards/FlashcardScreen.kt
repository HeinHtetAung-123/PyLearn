package com.example.pylearn.ui.flashcards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun FlashcardScreen(
    uiState: FlashcardUiState,
    onFlipCard: () -> Unit,
    onRemembered: () -> Unit,
    onReviewAgain: () -> Unit,
    onRestartSession: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        when {
            uiState.errorMessage != null -> {
                FlashcardErrorContent(
                    message = uiState.errorMessage,
                    onBackClick = onBackClick,
                    modifier = Modifier.padding(innerPadding)
                )
            }

            uiState.topic == null -> {
                FlashcardLoadingContent(
                    modifier = Modifier.padding(innerPadding)
                )
            }

            uiState.isSessionComplete -> {
                FlashcardCompletionContent(
                    uiState = uiState,
                    onRestartSession = onRestartSession,
                    onBackClick = onBackClick,
                    modifier = Modifier.padding(innerPadding)
                )
            }

            else -> {
                FlashcardLearningContent(
                    uiState = uiState,
                    onFlipCard = onFlipCard,
                    onRemembered = onRemembered,
                    onReviewAgain = onReviewAgain,
                    onBackClick = onBackClick,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
private fun FlashcardLearningContent(
    uiState: FlashcardUiState,
    onFlipCard: () -> Unit,
    onRemembered: () -> Unit,
    onReviewAgain: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val card = uiState.currentCard ?: return

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        TextButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text("Back")
        }

        Text(
            text = uiState.topic?.title.orEmpty(),
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Flashcard ${uiState.currentCardNumber} of ${uiState.totalCards}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        LinearProgressIndicator(
            progress = {
                uiState.progress.coerceIn(0f, 1f)
            },
            modifier = Modifier.fillMaxWidth()
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onFlipCard),
            colors = CardDefaults.cardColors(
                containerColor = if (uiState.isCardFlipped) {
                    MaterialTheme.colorScheme.secondaryContainer
                } else {
                    MaterialTheme.colorScheme.surfaceContainer
                }
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (uiState.isCardFlipped) {
                        "Answer"
                    } else {
                        "Prompt"
                    },
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = if (uiState.isCardFlipped) {
                        card.answer
                    } else {
                        card.prompt
                    },
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )

                if (!uiState.isCardFlipped && card.codeSnippet != null) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor =
                                MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Text(
                            text = card.codeSnippet,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyLarge,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                if (
                    uiState.isCardFlipped &&
                    card.explanation != null
                ) {
                    Text(
                        text = card.explanation,
                        style = MaterialTheme.typography.bodyMedium,
                        color =
                            MaterialTheme.colorScheme.onSecondaryContainer,
                        textAlign = TextAlign.Center
                    )
                }

                Text(
                    text = if (uiState.isCardFlipped) {
                        "Tap the card to view the prompt again."
                    } else {
                        "Take your time, then tap to reveal the answer."
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }

        if (uiState.isCardFlipped) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onReviewAgain,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Review Again")
                }

                Button(
                    onClick = onRemembered,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("I Remembered")
                }
            }
        } else {
            Button(
                onClick = onFlipCard,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Reveal Answer")
            }
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Remembered: ${uiState.rememberedCount}  •  Review again: ${uiState.reviewAgainCount}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun FlashcardCompletionContent(
    uiState: FlashcardUiState,
    onRestartSession: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Flashcard Review Complete",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = uiState.topic?.title.orEmpty(),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Remembered",
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    text = "${uiState.rememberedCount} of ${uiState.totalCards}",
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text =
                        "${uiState.reviewAgainCount} card(s) marked for another review.",
                    style = MaterialTheme.typography.bodyMedium,
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = onRestartSession,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Review Again")
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedButton(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Topics")
        }
    }
}

@Composable
private fun FlashcardErrorContent(
    message: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = onBackClick
        ) {
            Text("Back")
        }
    }
}

@Composable
private fun FlashcardLoadingContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}