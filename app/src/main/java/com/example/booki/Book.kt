package com.example.booki

import androidx.compose.ui.graphics.Color
import com.google.gson.Gson

class Book(
    var title: String="Unknown",
    var authors: List<String> = emptyList(),
    var numberOfPages: Int=-1,
    var publishDate: String="1.1.2000",
    var isbn10: String="",
    var isbn13: String="",
    val coverUrl: String="https://developer.valvesoftware.com/w/images/thumb/b/ba/CSGOErrorTextures.png/200px-CSGOErrorTextures.png",

    var subtitle: String="",
    val description: String="",
    var publisher: String="unknown publisher",
    val genres: List<String> = emptyList(),
    val language: String="unknown language",
    val source: String="none", // OpenLibrary, User
) {
    fun getISBN(): String? {
        return if (isbn10 != "") {
            isbn10
        } else if (isbn13 != "") {
            isbn13
        } else {
            null
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
    val id: Long=0L,
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
