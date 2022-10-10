package com.example.noteslist.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(note: Note)

    @Query("DELETE FROM note WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM note")
    fun getAll(): Flow<List<Note>>

    @Query("DELETE FROM note")
    suspend fun deleteAll()
}