package com.example.noteslist.ui.components.add_edit_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteslist.R
import com.example.noteslist.data.Note
import com.example.noteslist.repository.NoteRepository
import com.example.noteslist.utils.DispatchersProvider
import com.example.noteslist.utils.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesAddEditViewModel @Inject constructor(
    private val repository: NoteRepository,
    private val dispatchersProvider: DispatchersProvider,
    private val resourcesProvider: ResourcesProvider
) : ViewModel() {

    private val _toast = MutableSharedFlow<String>()
    val toast: SharedFlow<String> = _toast

    fun save(note: Note) = viewModelScope.launch(dispatchersProvider.io) {
        repository.save(note)
    }

    fun isNoteValid(
        note: Note
    ): Boolean =
        if (note.title.isNotBlank()) true
        else {
            viewModelScope.launch(dispatchersProvider.io) {
                _toast.emit(resourcesProvider.getString(R.string.toast_add_edit_message))
            }
            false
        }
}