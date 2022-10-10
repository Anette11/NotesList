package com.example.noteslist.di

import android.content.Context
import androidx.room.Room
import com.example.noteslist.data.NoteDao
import com.example.noteslist.data.NoteDatabase
import com.example.noteslist.repository.NoteRepository
import com.example.noteslist.repository.NoteRepositoryImpl
import com.example.noteslist.utils.DispatchersProvider
import com.example.noteslist.utils.NotePreferencesDataStore
import com.example.noteslist.utils.ResourcesProvider
import com.example.noteslist.utils.ResourcesProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(
        @ApplicationContext context: Context
    ): NoteDatabase =
        Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            NoteDatabase.name
        ).build()

    @Provides
    @Singleton
    fun provideNoteDao(
        noteDatabase: NoteDatabase
    ): NoteDao = noteDatabase.noteDao

    @Provides
    @Singleton
    fun provideNoteRepository(
        noteDao: NoteDao
    ): NoteRepository = NoteRepositoryImpl(noteDao)

    @Provides
    @Singleton
    fun provideDispatchersProvider(): DispatchersProvider =
        object : DispatchersProvider {
            override val io: CoroutineDispatcher
                get() = Dispatchers.IO
        }

    @Provides
    @Singleton
    fun provideNotePreferencesDataStore(
        @ApplicationContext context: Context
    ): NotePreferencesDataStore = NotePreferencesDataStore(context)

    @Provides
    fun provideResourcesProvider(
        @ApplicationContext context: Context
    ): ResourcesProvider = ResourcesProviderImpl(context)
}