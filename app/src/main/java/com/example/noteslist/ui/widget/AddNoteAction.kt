package com.example.noteslist.ui.widget

import android.content.Context
import android.content.Intent
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import com.example.noteslist.navigation.NoteDestination
import com.example.noteslist.ui.MainActivity
import com.example.noteslist.ui.utils.MainActivityIntentExtras

class AddNoteAction : ActionCallback {

    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        val intent = Intent(context, MainActivity::class.java)
            .apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra(
                    MainActivityIntentExtras.startDestination,
                    NoteDestination.NoteAddEditScreen.route
                )
            }
        context.startActivity(intent)
    }
}