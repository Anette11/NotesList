package com.example.noteslist.utils

import androidx.annotation.StringRes

interface ResourcesProvider {
    fun getString(@StringRes resId: Int): String
}