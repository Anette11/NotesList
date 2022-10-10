package com.example.noteslist.navigation

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.example.noteslist.data.Note
import com.example.noteslist.utils.GsonParser
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteAsArg(
    val id: Int,
    val title: String,
    val content: String
) : Parcelable {
    fun toNote(): Note =
        Note(
            id = id,
            title = title,
            content = content
        )

    companion object NavigationType : NavType<NoteAsArg>(isNullableAllowed = false) {
        override fun get(
            bundle: Bundle,
            key: String
        ): NoteAsArg? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(key, NoteAsArg::class.java)
            } else @Suppress("DEPRECATION") bundle.getParcelable(key)

        override fun parseValue(
            value: String
        ): NoteAsArg = GsonParser.fromJson(value, NoteAsArg::class.java)

        override fun put(
            bundle: Bundle,
            key: String,
            value: NoteAsArg
        ) = bundle.putParcelable(key, value)
    }
}