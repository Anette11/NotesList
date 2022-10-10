package com.example.noteslist.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            NoteDestination.NotesListScreen.route
        ) {
            inclusive = false
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

fun NavHostController.goBack(
    route: String,
    startDestinationByDefault: String = NoteDestination.NotesListScreen.route
) {
    if (this.navigateUp()) this.navigateUp()
    else {
        this.graph.setStartDestination(startDestinationByDefault)
        this.navigate(route = route) {
            popUpTo(0)
        }
    }
}