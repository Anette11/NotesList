package com.example.noteslist.ui.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.noteslist.R

@Composable
fun Dialog(
    title: String,
    text: String,
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit
) = AlertDialog(
    title = { Text(text = title, style = MaterialTheme.typography.h6) },
    text = { Text(text = text, style = MaterialTheme.typography.body1) },
    onDismissRequest = { },
    confirmButton = {
        TextButton(onClick = onConfirmClick) {
            Text(
                text = stringResource(id = R.string.dialog_confirm_button),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
        }
    },
    dismissButton = {
        TextButton(onClick = onDismissClick) {
            Text(
                text = stringResource(id = R.string.dialog_dismiss_button),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
        }
    }
)