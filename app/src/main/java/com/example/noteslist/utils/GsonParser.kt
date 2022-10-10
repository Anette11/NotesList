package com.example.noteslist.utils

import com.google.gson.Gson

object GsonParser : JsonParser {

    override fun <T> fromJson(json: String, classOfT: Class<T>): T =
        Gson().fromJson(json, classOfT)

    override fun <T> toJson(obj: T, classOfT: Class<T>): String =
        Gson().toJson(obj, classOfT)
}