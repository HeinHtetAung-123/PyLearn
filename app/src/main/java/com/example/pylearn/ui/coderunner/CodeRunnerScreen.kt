package com.example.pylearn.ui.coderunner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp

@Composable
fun CodeRunnerScreen(
    uiState: CodeRunnerUiState,
    onSourceCodeChanged: (String) -> Unit,
    onStandardInputChanged: (String) -> Unit,
    onRunCode: () -> Unit,
    onResetCode: () -> Unit,
    onBackClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Python Code Runner",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        item {
            Text(
                text = "Write Python code, run it online and review the result.",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        item {
            PrivacyNoticeCard()
        }

        item {
            OutlinedTextField(
                value = uiState.sourceCode,
                onValueChange = onSourceCodeChanged,
                label = {
                    Text(text = "Python code")
                },
                modifier = Modifier.fillMaxWidth(),
                minLines = 10,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = FontFamily.Monospace
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrectEnabled = false
                )
            )
        }

        item {
            OutlinedTextField(
                value = uiState.standardInput,
                onValueChange = onStandardInputChanged,
                label = {
                    Text(text = "Standard input — optional")
                },
                supportingText = {
                    Text(
                        text = "Enter values here when your program uses input()."
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = FontFamily.Monospace
                )
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onRunCode,
                    enabled = uiState.canRunCode,
                    modifier = Modifier.weight(1f)
                ) {
                    if (uiState.isRunning) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(2.dp)
                        )
                    } else {
                        Text(text = "Run Code")
                    }
                }

                OutlinedButton(
                    onClick = onResetCode,
                    enabled = !uiState.isRunning,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Reset")
                }
            }
        }

        uiState.executionResult?.let { result ->
            item {
                ExecutionResultCard(
                    output = result.output,
                    status = result.statusDescription,
                    executionTime = result.executionTime,
                    memoryUsedKb = result.memoryUsedKb,
                    isSuccessful = result.isSuccessful
                )
            }
        }

        uiState.errorMessage?.let { errorMessage ->
            item {
                ErrorCard(message = errorMessage)
            }
        }

        item {
            OutlinedButton(
                onClick = onBackClick,
                enabled = !uiState.isRunning,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Back to Home")
            }
        }
    }
}

@Composable
private fun PrivacyNoticeCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "External code execution",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Code entered here is sent to an external execution service. Do not enter passwords, personal details or confidential information.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun ExecutionResultCard(
    output: String,
    status: String,
    executionTime: String?,
    memoryUsedKb: Int?,
    isSuccessful: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isSuccessful) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.errorContainer
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = if (isSuccessful) {
                    "Execution successful"
                } else {
                    "Execution issue"
                },
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "Status: $status",
                style = MaterialTheme.typography.labelLarge
            )

            Text(
                text = output,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(14.dp),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = FontFamily.Monospace
                )
            )

            executionTime?.let {
                Text(
                    text = "Execution time: $it seconds",
                    style = MaterialTheme.typography.labelMedium
                )
            }

            memoryUsedKb?.let {
                Text(
                    text = "Memory used: $it KB",
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@Composable
private fun ErrorCard(
    message: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Unable to run code",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}