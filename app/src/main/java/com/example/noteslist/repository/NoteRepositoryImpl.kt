package com.example.noteslist.repository

import com.example.noteslist.data.Note
import com.example.noteslist.data.NoteDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {

    override suspend fun save(note: Note) = noteDao.save(note)

    override suspend fun delete(id: Int) = noteDao.delete(id)

    override suspend fun deleteAll() = noteDao.deleteAll()

    override fun getAll(): Flow<List<Note>> = noteDao.getAll()
}