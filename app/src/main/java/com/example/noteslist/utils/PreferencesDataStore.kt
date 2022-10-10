package com.example.noteslist.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class NotePreferencesDataStore(
    private val context: Context
) {
    private val preferencesDataStoreName = "preferences_data_store_name"
    private val preferencesKeySort = stringPreferencesKey("preferences_key_sort")

    private val Context.dataStore: DataStore<Preferences> by
    preferencesDataStore(preferencesDataStoreName)

    suspend fun saveSort(
        sort: Sort
    ) = context.dataStore.edit { mutablePreferences ->
        mutablePreferences[preferencesKeySort] = sort.sort
    }

    suspend fun getSort(): String? {
        val preferences = context.dataStore.data.first()
        return preferences[preferencesKeySort]
    }
}