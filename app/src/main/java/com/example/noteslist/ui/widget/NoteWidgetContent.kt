package com.example.noteslist.ui.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.Column
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.noteslist.R

@Composable
fun NoteWidgetContent(
) {
    val context = LocalContext.current
    Column(
        modifier = GlanceModifier
            .clickable(onClick = actionRunCallback<AddNoteAction>())
            .background(color = Color(0xFFF1DCC9))
            .padding(16.dp)
            .cornerRadius(8.dp)
    ) {
        Text(
            text = context.getString(R.string.widget_button_text),
            style = TextStyle(
                color = ColorProvider(color = Color(0xFF9F4636)),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        )
    }
}