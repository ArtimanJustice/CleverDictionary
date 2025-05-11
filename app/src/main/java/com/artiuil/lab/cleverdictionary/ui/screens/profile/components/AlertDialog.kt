package com.artiuil.lab.cleverdictionary.ui.screens.profile.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable

@Composable
fun EditUsernameDialog(
    currentName: String,
    onNameChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Edit Username") },
        text = {
            TextField(
                value = currentName,
                onValueChange = onNameChange,
                label = { Text(text = "Username") }
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    )
}