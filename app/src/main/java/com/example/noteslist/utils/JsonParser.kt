package com.example.noteslist.utils

interface JsonParser {

    fun <T> fromJson(json: String, classOfT: Class<T>): T?

    fun <T> toJson(obj: T, classOfT: Class<T>): String?
}