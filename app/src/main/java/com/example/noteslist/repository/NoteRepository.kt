package com.example.noteslist.repository

import com.example.noteslist.data.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun save(note: Note)

    suspend fun delete(id: Int)

    suspend fun deleteAll()

    fun getAll(): Flow<List<Note>>
}