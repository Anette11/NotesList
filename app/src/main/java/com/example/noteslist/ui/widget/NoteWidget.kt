package com.example.noteslist.ui.widget

import androidx.compose.runtime.Composable
import androidx.glance.appwidget.GlanceAppWidget

class NoteWidget : GlanceAppWidget() {

    @Composable
    override fun Content() = NoteWidgetContent()
}