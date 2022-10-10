package com.example.noteslist.navigation

sealed class NoteDestination(
    val route: String
) {
    object NotesListScreen : NoteDestination(route = "notes_list_screen")

    object NoteAddEditScreen : NoteDestination(route = "note_add_edit_screen") {
        const val argNote = "note"
    }
}