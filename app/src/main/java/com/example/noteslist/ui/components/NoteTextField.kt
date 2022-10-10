package com.example.noteslist.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle

@Composable
fun NoteTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    isHintVisible: Boolean,
    onFocusChange: (FocusState) -> Unit,
    textStyle: TextStyle,
    maxLines: Int? = null
) = Box {
    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { onFocusChange(it) },
        value = value,
        onValueChange = { onValueChange(it) },
        textStyle = textStyle,
        maxLines = maxLines ?: Int.MAX_VALUE
    )
    if (isHintVisible) {
        Text(
            text = hint,
            style = textStyle
        )
    }
}