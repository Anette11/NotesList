package com.example.noteslist.utils

import android.content.Context
import androidx.annotation.StringRes

class ResourcesProviderImpl(
    private val context: Context
) : ResourcesProvider {

    override fun getString(
        @StringRes resId: Int
    ) = context.getString(resId)
}