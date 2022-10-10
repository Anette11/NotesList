package com.example.noteslist.ui.components.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteslist.data.Note
import com.example.noteslist.repository.NoteRepository
import com.example.noteslist.utils.DispatchersProvider
import com.example.noteslist.utils.NotePreferencesDataStore
import com.example.noteslist.utils.Sort
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val repository: NoteRepository,
    private val dispatchersProvider: DispatchersProvider,
    private val notePreferencesDataStore: NotePreferencesDataStore
) : ViewModel() {

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    private val sort = MutableStateFlow(Sort.TITLE)

    private val _snackBar = MutableSharedFlow<Unit>()
    val snackBar: SharedFlow<Unit> = _snackBar

    private var deletedNote: Note? = null

    init {
        viewModelScope.launch(dispatchersProvider.io) {
            repository.getAll().collect { notes ->
                _notes.emit(notes.sort(sort.value))
            }
        }
        viewModelScope.launch(dispatchersProvider.io) {
            sort.collect { sort ->
                _notes.update { notes -> notes.sort(sort) }
            }
        }
        viewModelScope.launch(dispatchersProvider.io) {
            sort.emit(Sort.getSort(notePreferencesDataStore.getSort()))
        }
    }

    fun delete(note: Note) = viewModelScope.launch(dispatchersProvider.io) {
        deletedNote = note
        repository.delete(note.id)
        _snackBar.emit(Unit)
    }

    fun deleteAll() = viewModelScope.launch(dispatchersProvider.io) {
        repository.deleteAll()
    }

    fun save() = viewModelScope.launch(dispatchersProvider.io) {
        deletedNote?.let {
            repository.save(Note(title = it.title, content = it.content))
        }
    }

    fun updateSort(sort: Sort) = viewModelScope.launch(dispatchersProvider.io) {
        this@NotesListViewModel.sort.emit(sort)
        notePreferencesDataStore.saveSort(sort)
    }

    private fun List<Note>.sort(sort: Sort): List<Note> =
        this.sortedBy { note ->
            when (sort) {
                Sort.TITLE -> note.title
                Sort.CONTENT -> note.content
            }
        }
}

