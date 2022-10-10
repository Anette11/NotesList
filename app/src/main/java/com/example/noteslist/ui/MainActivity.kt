package com.example.noteslist.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.noteslist.navigation.NoteDestination
import com.example.noteslist.navigation.NotesNavHost
import com.example.noteslist.ui.theme.NotesListTheme
import com.example.noteslist.ui.utils.MainActivityIntentExtras
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesListTheme {
                NotesNavHost(
                    startDestination = intent.getStringExtra(MainActivityIntentExtras.startDestination)
                        ?: NoteDestination.NotesListScreen.route
                )
            }
        }
    }
}