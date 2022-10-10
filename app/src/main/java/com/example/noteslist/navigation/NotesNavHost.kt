package com.example.noteslist.navigation

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.noteslist.ui.components.add_edit_screen.NoteAddEditScreen
import com.example.noteslist.ui.components.main_screen.NotesListScreen

@Composable
fun NotesNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = NoteDestination.NotesListScreen.route
) = NavHost(
    navController = navController,
    startDestination = startDestination
) {
    composable(
        route = NoteDestination.NotesListScreen.route
    ) {
        NotesListScreen(
            navController = navController
        )
    }
    composable(
        route = NoteDestination.NoteAddEditScreen.route
    ) {
        NoteAddEditScreen(
            navController = navController
        )
    }
    composable(
        route = NoteDestination.NoteAddEditScreen.route
            .plus("/{${NoteDestination.NoteAddEditScreen.argNote}}"),
        arguments = listOf(
            navArgument(name = NoteDestination.NoteAddEditScreen.argNote) {
                type = NoteAsArg.NavigationType
            }
        )
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            it.arguments?.getParcelable(
                NoteDestination.NoteAddEditScreen.argNote,
                NoteAsArg::class.java
            )
        } else {
            @Suppress("DEPRECATION")
            it.arguments?.getParcelable(
                NoteDestination.NoteAddEditScreen.argNote
            )
        }?.let { noteAsArg ->
            NoteAddEditScreen(
                navController = navController,
                argNote = noteAsArg.toNote()
            )
        }
    }
}