package com.example.pylearn.ui.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pylearn.domain.model.LearningQuestion

@Composable
fun ActivityScreen(
    uiState: ActivityUiState,
    onAnswerSelected: (Int) -> Unit,
    onSubmitAnswer: () -> Unit,
    onNextQuestion: () -> Unit,
    onRestartQuiz: () -> Unit,
    onBackClick: () -> Unit
) {
    when {
        uiState.isLoading -> {
            MessageScreen(message = "Loading activity...")
        }

        uiState.errorMessage != null -> {
            MessageScreen(
                message = uiState.errorMessage,
                onBackClick = onBackClick
            )
        }

        uiState.isQuizComplete -> {
            QuizCompleteScreen(
                uiState = uiState,
                onRestartQuiz = onRestartQuiz,
                onBackClick = onBackClick
            )
        }

        uiState.currentQuestion != null -> {
            QuizContent(
                uiState = uiState,
                onAnswerSelected = onAnswerSelected,
                onSubmitAnswer = onSubmitAnswer,
                onNextQuestion = onNextQuestion,
                onBackClick = onBackClick
            )
        }
    }
}

@Composable
private fun QuizContent(
    uiState: ActivityUiState,
    onAnswerSelected: (Int) -> Unit,
    onSubmitAnswer: () -> Unit,
    onNextQuestion: () -> Unit,
    onBackClick: () -> Unit
) {
    val question = uiState.currentQuestion ?: return

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = uiState.topic?.title.orEmpty(),
                style = MaterialTheme.typography.headlineSmall
            )
        }

        item {
            Text(
                text = "Question ${uiState.questionNumber} of ${uiState.totalQuestions}",
                style = MaterialTheme.typography.labelLarge
            )
        }

        item {
            LinearProgressIndicator(
                progress = { uiState.progress },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            QuestionCard(question = question)
        }

        items(question.options.size) { answerIndex ->
            AnswerButton(
                text = question.options[answerIndex],
                answerIndex = answerIndex,
                selectedAnswerIndex = uiState.selectedAnswerIndex,
                correctAnswerIndex = question.correctAnswerIndex,
                isAnswerSubmitted = uiState.isAnswerSubmitted,
                onAnswerSelected = onAnswerSelected
            )
        }

        if (uiState.isAnswerSubmitted) {
            item {
                FeedbackCard(
                    question = question,
                    selectedAnswerIndex = uiState.selectedAnswerIndex
                )
            }
        }

        item {
            if (uiState.isAnswerSubmitted) {
                Button(
                    onClick = onNextQuestion,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (uiState.questionNumber == uiState.totalQuestions) {
                            "Finish Activity"
                        } else {
                            "Next Question"
                        }
                    )
                }
            } else {
                Button(
                    onClick = onSubmitAnswer,
                    enabled = uiState.selectedAnswerIndex != null,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Submit Answer")
                }
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
private fun QuestionCard(
    question: LearningQuestion
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = question.questionText,
                style = MaterialTheme.typography.titleLarge
            )

            question.codeSnippet?.let { code ->
                Text(
                    text = code,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
private fun AnswerButton(
    text: String,
    answerIndex: Int,
    selectedAnswerIndex: Int?,
    correctAnswerIndex: Int,
    isAnswerSubmitted: Boolean,
    onAnswerSelected: (Int) -> Unit
) {
    val buttonText = when {
        isAnswerSubmitted && answerIndex == correctAnswerIndex ->
            "✓ $text"

        isAnswerSubmitted && answerIndex == selectedAnswerIndex ->
            "✗ $text"

        else -> text
    }

    OutlinedButton(
        onClick = {
            onAnswerSelected(answerIndex)
        },
        enabled = !isAnswerSubmitted,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = buttonText)
    }
}

@Composable
private fun FeedbackCard(
    question: LearningQuestion,
    selectedAnswerIndex: Int?
) {
    val isCorrect = selectedAnswerIndex == question.correctAnswerIndex

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isCorrect) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.errorContainer
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = if (isCorrect) {
                    "Correct"
                } else {
                    "Not quite"
                },
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = question.explanation,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun QuizCompleteScreen(
    uiState: ActivityUiState,
    onRestartQuiz: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(
            space = 20.dp,
            alignment = androidx.compose.ui.Alignment.CenterVertically
        )
    ) {
        Text(
            text = "Activity Complete",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Topic: ${uiState.topic?.title.orEmpty()}",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "Score: ${uiState.score} out of ${uiState.totalQuestions}",
            style = MaterialTheme.typography.headlineSmall
        )

        Button(
            onClick = onRestartQuiz,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Try Again")
        }

        OutlinedButton(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Back to Home")
        }
    }
}

@Composable
private fun MessageScreen(
    message: String,
    onBackClick: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = androidx.compose.ui.Alignment.CenterVertically
        )
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge
        )

        onBackClick?.let {
            Button(
                onClick = it,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Back to Home")
            }
        }
    }
}