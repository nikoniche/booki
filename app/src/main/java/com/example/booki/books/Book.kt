package com.example.booki.books

import androidx.compose.ui.graphics.Color
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
}

data class PersonalBook(
    val book: Book,
    var status: Status=Status.PlanToRead,
    val readPages: Int=0,
    val rating: Int=1, // 1-10 / 2 na 5 hvezdicek
    val comment: String="",
)

sealed class Status(
    val inText: String,
    val color: Color,
) {
    data object PlanToRead : Status("Plan to read", Color.Gray)
    data object Reading : Status("Reading", Color.Yellow)
    data object Finished : Status("Finished", Color.Green)
    data object Dropped : Status("Dropped", Color.Red)
}

val dummyBook: Book = Book(
    title="Dummy book",
    author="Dummy author",
    numberOfPages = 129,
    publishDate = "1.3.2034",
    isbn10="0141023996", // strictly isbn-10 (cause its fetched by the API)
    description = "no description :(",
)

val dummyPersonalBook: PersonalBook = PersonalBook(
    book=dummyBook,
    status=Status.PlanToRead,
    readPages=122,
    rating=7,
    comment="was alright",
)

val dummyPersonalBooks = listOf(dummyPersonalBook, dummyPersonalBook.copy(status=Status.Finished), dummyPersonalBook.copy(), dummyPersonalBook.copy(status=Status.Dropped), dummyPersonalBook, dummyPersonalBook, dummyPersonalBook)
