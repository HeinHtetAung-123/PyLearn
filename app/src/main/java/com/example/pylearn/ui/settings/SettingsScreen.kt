package com.example.pylearn.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    uiState: SettingsUiState,
    onDarkModeChanged: (Boolean) -> Unit,
    onLargeTextChanged: (Boolean) -> Unit,
    onConfirmBeforeResetChanged: (Boolean) -> Unit,
    onSoundEffectsChanged: (Boolean) -> Unit,
    onResetProgressClick: () -> Unit,
    onConfirmReset: () -> Unit,
    onDismissReset: () -> Unit,
    onBackClick: () -> Unit
) {
    if (uiState.isLoading) {
        SettingsLoadingScreen()
        return
    }

    if (uiState.showResetConfirmation) {
        ResetProgressDialog(
            onConfirm = onConfirmReset,
            onDismiss = onDismissReset
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        item {
            Text(
                text = "Personalise the learning experience and manage saved progress.",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        item {
            Text(
                text = "Appearance and accessibility",
                style = MaterialTheme.typography.titleLarge
            )
        }

        item {
            SettingSwitchCard(
                title = "Sound effects",
                description =
                    "Play subtle sounds for answers and activity completion.",
                checked = uiState.soundEffectsEnabled,
                onCheckedChange = onSoundEffectsChanged
            )
        }

        item {
            SettingSwitchCard(
                title = "Dark mode",
                description = "Use a darker colour scheme throughout PyLearn.",
                checked = uiState.darkModeEnabled,
                onCheckedChange = onDarkModeChanged
            )
        }

        item {
            SettingSwitchCard(
                title = "Larger text",
                description = "Increase text size to improve readability.",
                checked = uiState.largeTextEnabled,
                onCheckedChange = onLargeTextChanged
            )
        }

        item {
            Text(
                text = "Progress management",
                style = MaterialTheme.typography.titleLarge
            )
        }

        item {
            SettingSwitchCard(
                title = "Confirm before reset",
                description = "Show a warning before deleting quiz progress.",
                checked = uiState.confirmBeforeReset,
                onCheckedChange = onConfirmBeforeResetChanged
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Reset learning progress",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "Delete all saved scores, attempts and completed-topic records.",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Button(
                        onClick = onResetProgressClick,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Reset Progress")
                    }
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
private fun SettingSwitchCard(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

@Composable
private fun ResetProgressDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Reset progress?")
        },
        text = {
            Text(
                text = "This will permanently delete all saved quiz scores and attempts."
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Reset")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    )
}

@Composable
private fun SettingsLoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}