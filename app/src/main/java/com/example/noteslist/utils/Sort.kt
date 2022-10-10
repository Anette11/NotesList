package com.example.noteslist.utils

enum class Sort(val sort: String) {
    TITLE(sort = "Title"),
    CONTENT(sort = "Content");

    companion object {
        fun getSort(
            sort: String?
        ): Sort = values().find { it.sort == sort } ?: CONTENT
    }
}