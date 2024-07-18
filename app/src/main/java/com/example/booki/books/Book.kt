package com.example.booki.books

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
    val status: String="Not read",
    val readPages: Int=0,
    val rating: Int=1, // 1-10 / 2 na 5 hvezdicek
    val comment: String="",
)

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
    status="Reading",
    readPages=122,
    rating=7,
    comment="was alright",
)

val dummyPersonalBooks = listOf(dummyPersonalBook, dummyPersonalBook.copy(status="Finished"), dummyPersonalBook.copy(status="Not read"), dummyPersonalBook.copy(status="Dropped"), dummyPersonalBook, dummyPersonalBook, dummyPersonalBook)
