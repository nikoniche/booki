package com.example.booki

import androidx.compose.ui.graphics.Color
import com.google.gson.Gson

class Book(
    var id: Long=-1L, // for user books, do not need to care with API books

    var title: String,
    var authors: List<String> = emptyList(),
    var numberOfPages: Int,
    var publishDate: String="",
    var isbn10: String="",
    var isbn13: String="",
    val coverUrl: String="https://developer.valvesoftware.com/w/images/thumb/b/ba/CSGOErrorTextures.png/200px-CSGOErrorTextures.png",

    var subtitle: String="",
    val description: String="",
    var publisher: String="",
    val genres: List<String> = emptyList(),
    val language: String="",
    val source: String="", // OpenLibrary, User
) {
    fun getISBN(): String {
        return if (isbn10 != "") {
            isbn10
        } else if (isbn13 != "") {
            isbn13
        } else {
            ""
        }
    }

    fun textify(): String {
        return Gson().toJson(this)
    }

    fun getAuthors(): String {
        var text = ""
        this.authors.forEach {
            text += "$it, "
        }
        try {
            return text.substring(0, text.length - 2)
        }
        catch (e: Exception) {
            return "AUTHOR ERROR"
        }
    }
}

data class PersonalBook(
    val id: Long=-1L,
    val book: Book,
    var status: Status = Status.PlanToRead,
    var readPages: Int=0,
    var rating: Int=1, // 1-10 / 2 na 5 hvezdicek
    var review: String="",
    var bookNotes: String="",
)

sealed class Status(
    val id: Int,
    val inText: String,
    val color: Color,
) {
    data object PlanToRead : Status(id=0, "Plan to read", Color.Gray)
    data object Reading : Status(id=1, "Reading", Color.Yellow)
    data object Finished : Status(id=2, "Finished", Color.Green)
    data object Dropped : Status(id=3, "Dropped", Color.Red)
}
