package com.example.noteslist.ui.components.add_edit_screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.noteslist.R
import com.example.noteslist.data.Note
import com.example.noteslist.navigation.NoteDestination
import com.example.noteslist.navigation.goBack
import com.example.noteslist.ui.components.NoteTextField
import com.example.noteslist.ui.utils.NoteTextFieldState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NoteAddEditScreen(
    navController: NavHostController,
    viewModel: NotesAddEditViewModel = hiltViewModel(),
    argNote: Note? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val hintTitle = stringResource(id = R.string.hint_title)
    val hintContent = stringResource(id = R.string.hint_content)
    var note by remember { mutableStateOf(argNote ?: Note(title = "", content = "")) }
    val context = LocalContext.current
    var titleTextFieldState by remember {
        mutableStateOf(
            NoteTextFieldState(
                hint = hintTitle,
                isHintVisible = true
            )
        )
    }
    var contentTextFieldState by remember {
        mutableStateOf(
            NoteTextFieldState(
                hint = hintContent,
                isHintVisible = true
            )
        )
    }

    LaunchedEffect(key1 = true) {
        viewModel.toast.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.notes)) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            focusManager.clearFocus()
                            keyboardController?.hide()
                            navController.goBack(route = NoteDestination.NotesListScreen.route)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (viewModel.isNoteValid(note)) {
                        viewModel.save(note)
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        navController.goBack(route = NoteDestination.NotesListScreen.route)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Save,
                    contentDescription = stringResource(id = R.string.save_note)
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(dimensionResource(id = R.dimen._8dp))
        ) {
            NoteTextField(
                value = note.title,
                onValueChange = { titleChanged ->
                    note = note.copy(title = titleChanged)
                },
                hint = titleTextFieldState.hint,
                isHintVisible = titleTextFieldState.isHintVisible,
                onFocusChange = { focusState ->
                    titleTextFieldState = titleTextFieldState
                        .copy(isHintVisible = !focusState.isFocused && note.title.isEmpty())
                },
                textStyle = MaterialTheme.typography.h6,
                maxLines = 5
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen._8dp)))
            NoteTextField(
                value = note.content,
                onValueChange = { contentChanged ->
                    note = note.copy(content = contentChanged)
                },
                hint = contentTextFieldState.hint,
                isHintVisible = contentTextFieldState.isHintVisible,
                onFocusChange = { focusState ->
                    contentTextFieldState = contentTextFieldState
                        .copy(isHintVisible = !focusState.isFocused && note.content.isEmpty())
                },
                textStyle = MaterialTheme.typography.body1
            )
        }
    }
}