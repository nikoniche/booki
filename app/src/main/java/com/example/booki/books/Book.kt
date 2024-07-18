package com.example.booki.books

import androidx.compose.ui.graphics.Color

data class Book(
    val title: String="Unknown",
    val author: String="Anonymous",
    val numberOfPages: Int=-1,
    val publishDate: String="1.1.2000",
    val isbn: String="",
    val description: String="",
    val coverUrl: String="https://developer.valvesoftware.com/w/images/thumb/b/ba/CSGOErrorTextures.png/200px-CSGOErrorTextures.png",
)

data class PersonalBook(
    val book: Book,
    val status: Status=Status.NotRead,
    val readPages: Int=0,
    val rating: Int=1, // 1-10 / 2 na 5 hvezdicek
    val comment: String="",
)

sealed class Status(
    val inText: String,
    val color: Color,
) {
    data object NotRead : Status("Not read", Color.Gray)
    data object Reading : Status("Reading", Color.Yellow)
    data object Finished : Status("Finished", Color.Green)
    data object Dropped : Status("Dropped", Color.Red)
}

val dummyBook: Book = Book(
    title="Dummy book",
    author="Dummy author",
    numberOfPages = 129,
    publishDate = "1.3.2034",
    isbn="0141023996", // strictly isbn-10 (cause its fetched by the API)
    description = "no description :(",
)

val dummyPersonalBook: PersonalBook = PersonalBook(
    book=dummyBook,
    status=Status.NotRead,
    readPages=122,
    rating=7,
    comment="was alright",
)

val dummyPersonalBooks = listOf(dummyPersonalBook, dummyPersonalBook.copy(status=Status.Finished), dummyPersonalBook.copy(), dummyPersonalBook.copy(status=Status.Dropped), dummyPersonalBook, dummyPersonalBook, dummyPersonalBook)
