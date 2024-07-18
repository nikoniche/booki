package com.example.booki.personalData

import androidx.lifecycle.ViewModel
import com.example.booki.books.Book
import com.example.booki.books.PersonalBook
import com.example.booki.books.Status
import com.example.booki.books.dummyPersonalBook

object PersonalRecordsViewModel: ViewModel() {
    private val books: MutableList<PersonalBook> = mutableListOf(
          dummyPersonalBook.copy(status= Status.Reading, rating=10),
          dummyPersonalBook.copy(status=Status.Dropped),
          dummyPersonalBook.copy(status=Status.Finished),
          dummyPersonalBook.copy(status=Status.Finished),
          dummyPersonalBook,
          dummyPersonalBook,
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

    fun getBooks(status: Status?=null): List<PersonalBook> {
        // null status to allow for getting books of all types
        return if (status == null) books
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
            if (personalBook.book == book) return personalBook
        }
        return null
    }
}