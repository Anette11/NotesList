package com.example.noteslist.ui.components.main_screen

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.noteslist.R
import com.example.noteslist.data.Note
import com.example.noteslist.navigation.NoteAsArg
import com.example.noteslist.navigation.NoteDestination
import com.example.noteslist.navigation.navigateSingleTopTo
import com.example.noteslist.ui.components.Dialog
import com.example.noteslist.ui.components.NoteCard
import com.example.noteslist.utils.GsonParser
import com.example.noteslist.utils.Sort
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NotesListScreen(
    navController: NavHostController,
    viewModel: NotesListViewModel = hiltViewModel()
) {
    val notes = viewModel.notes.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val snackBarMessage = stringResource(id = R.string.snack_bar_message)
    val snackBarActionLabel = stringResource(id = R.string.snack_bar_action_label)
    var snackBarJob: Job? = null

    LaunchedEffect(key1 = true) {
        viewModel.snackBar.collect {
            snackBarJob?.cancel()
            snackBarJob = coroutineScope.launch {
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = snackBarMessage,
                    actionLabel = snackBarActionLabel
                )
                if (snackBarResult == SnackbarResult.ActionPerformed) viewModel.save()
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.notes)) },
                actions = {
                    IconButton(
                        onClick = { showMenu = !showMenu }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Sort,
                            contentDescription = stringResource(id = R.string.sort)
                        )
                    }
                    IconButton(
                        onClick = { showDialog = !showDialog }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = stringResource(id = R.string.delete_all)
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = !showMenu }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                viewModel.updateSort(Sort.TITLE)
                                showMenu = !showMenu
                            }
                        ) {
                            Text(text = stringResource(id = R.string.dropdown_menu_item_title))
                        }
                        DropdownMenuItem(
                            onClick = {
                                viewModel.updateSort(Sort.CONTENT)
                                showMenu = !showMenu
                            }
                        ) {
                            Text(text = stringResource(id = R.string.dropdown_menu_item_content))
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigateSingleTopTo(NoteDestination.NoteAddEditScreen.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.add_note)
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colors.background)
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn {
                    items(
                        items = notes.value,
                        key = { note: Note -> note.id }
                    ) { note ->
                        val dismissState = rememberDismissState()
                        if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                            viewModel.delete(note)
                        }
                        SwipeToDismiss(
                            state = dismissState,
                            background = {
                                val direction =
                                    dismissState.dismissDirection ?: return@SwipeToDismiss
                                if (direction == DismissDirection.EndToStart) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(color = Color.Transparent)
                                            .padding(dimensionResource(id = R.dimen._8dp))
                                    ) {
                                        Column(
                                            modifier = Modifier.align(Alignment.CenterEnd)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = stringResource(id = R.string.delete_note),
                                                tint = colorResource(id = R.color.gray),
                                                modifier = Modifier.align(Alignment.CenterHorizontally)
                                            )
                                            Text(
                                                text = stringResource(id = R.string.swipe_description),
                                                textAlign = TextAlign.Center,
                                                color = colorResource(id = R.color.gray)
                                            )
                                        }
                                    }
                                }
                            },
                            dismissContent = {
                                NoteCard(
                                    title = note.title,
                                    content = note.content,
                                    onClick = {
                                        val noteString = Uri.encode(
                                            GsonParser.toJson(
                                                note.toNoteAsArg(),
                                                NoteAsArg::class.java
                                            )
                                        )
                                        navController.navigateSingleTopTo(
                                            NoteDestination.NoteAddEditScreen.route.plus("/$noteString")
                                        )
                                    }
                                )
                            },
                            directions = setOf(DismissDirection.EndToStart),
                            dismissThresholds = { direction ->
                                FractionalThreshold(if (direction == DismissDirection.EndToStart) 1f else 0f)
                            }
                        )
                    }
                }
            }
            if (showDialog) {
                Dialog(
                    title = stringResource(id = R.string.dialog_title),
                    text = stringResource(id = R.string.dialog_text),
                    onConfirmClick = {
                        showDialog = !showDialog
                        viewModel.deleteAll()
                    },
                    onDismissClick = {
                        showDialog = !showDialog
                    }
                )
            }
        }
    }
}
