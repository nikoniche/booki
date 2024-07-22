package com.example.booki

import androidx.compose.ui.graphics.Color
import com.google.gson.Gson

class Book(
    val title: String="Unknown",
    val author: String="Anonymous",
    val numberOfPages: Int=-1,
    val publishDate: String="1.1.2000",
    val isbn10: String="",
    val isbn13: String="",
    val description: String="",
    val coverUrl: String="https://developer.valvesoftware.com/w/images/thumb/b/ba/CSGOErrorTextures.png/200px-CSGOErrorTextures.png",
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
}

data class PersonalBook(
    val id: Long=0L,
    val book: Book,
    var status: Status = Status.PlanToRead,
    var readPages: Int=0,
    var rating: Int=1, // 1-10 / 2 na 5 hvezdicek
    var review: String="",
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
