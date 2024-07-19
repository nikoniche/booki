package com.example.booki.personalData

import androidx.lifecycle.ViewModel
import com.example.booki.books.Book
import com.example.booki.books.PersonalBook
import com.example.booki.books.Status
import com.example.booki.books.dummyPersonalBook

object PersonalRecordsViewModel: ViewModel() {
    private val books: MutableList<PersonalBook> = mutableListOf(
    )

    fun removeBook(
        personalBook: PersonalBook
    ) {
        books.remove(personalBook)
    }

    fun getPersonalBookByBook(book: Book): PersonalBook? {
        books.forEach {
            personalBook ->
            if (personalBook.book == book) {
                return personalBook
            }
        }
        return null
    }
    fun changeBookStatus(book: Book, status: Status?) {
        val personalBook: PersonalBook? = getPersonalBookByBook(book)
        if (personalBook == null) {
            val newPersonalBook: PersonalBook = PersonalBook(
                book=book,
                status=status as Status // assuming we arent trying to delete non-existent book
            )
            books.add(newPersonalBook)
        } else {
            when (status) {
                null -> removeBook(personalBook)
                else -> personalBook.status = status
            }
        }
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