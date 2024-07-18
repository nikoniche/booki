package com.example.booki.personalData

import androidx.lifecycle.ViewModel
import com.example.booki.books.Book
import com.example.booki.books.PersonalBook
import com.example.booki.books.dummyPersonalBook

object PersonalRecordsViewModel: ViewModel() {
    private val books: MutableList<PersonalBook> = mutableListOf(
        dummyPersonalBook,
        dummyPersonalBook.copy(status="Not read"),
        dummyPersonalBook.copy(status="Dropped", rating=1),
        dummyPersonalBook.copy(status="Finished", rating=10),
        dummyPersonalBook,
        dummyPersonalBook,
        dummyPersonalBook.copy(status="Not read"),
        dummyPersonalBook.copy(status="Dropped", rating=3),
        dummyPersonalBook.copy(status="Dropped", rating=3),
        dummyPersonalBook.copy(status="Dropped", rating=3),
        dummyPersonalBook.copy(status="Finished", rating=9)
    )

    fun addBook(
        personalBook: PersonalBook
    ) {
        books.add(personalBook)
    }

    fun removeBook(
        personalBook: PersonalBook
    ) {
        books.remove(personalBook)
    }

    fun getBooks(status: String="all"): List<PersonalBook> {
        return if (status == "all") books
        else {
            val matchingBooks: MutableList<PersonalBook> = mutableListOf()
            books.forEach {
                    book ->
                if (book.status == status) matchingBooks.add(book)
            }
            matchingBooks
        }
    }

    fun getPersonalRecordsForGeneralBook(book: Book): PersonalBook? {
        books.forEach {
            personalBook ->
            if (personalBook.book.equals(book)) return personalBook
        }
        return null
    }
}