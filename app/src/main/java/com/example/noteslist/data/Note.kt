package com.example.noteslist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.noteslist.navigation.NoteAsArg

@Entity(tableName = "note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String
) {
    fun toNoteAsArg(): NoteAsArg =
        NoteAsArg(
            id = id,
            title = title,
            content = content
        )
}