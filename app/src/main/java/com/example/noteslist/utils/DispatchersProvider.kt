package com.example.noteslist.utils

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersProvider {
    val io: CoroutineDispatcher
}