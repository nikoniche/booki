package com.example.booki.personalData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booki.books.Book
import com.example.booki.books.PersonalBook
import com.example.booki.books.Status
import com.example.booki.local_database.Graph
import com.example.booki.local_database.PersonalBookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonalRecordsViewModel: ViewModel() {
    private val books: MutableList<PersonalBook> = mutableListOf(
    )
    private val personalBookRepository: PersonalBookRepository = Graph.personalBookRepository

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val listOfSavedBooks = personalBookRepository.getPersonalBooks()
            books.addAll(listOfSavedBooks)
        }
    }
    fun removeBook(
        personalBook: PersonalBook
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            personalBookRepository.deletePersonalBook(personalBook)
        }

        books.remove(personalBook)
    }

    fun getPersonalBookByBook(book: Book): PersonalBook? {
        books.forEach {
            personalBook ->
            if (personalBook.book.getISBN() == book.getISBN()) {
                return personalBook
            }
        }
        return null
    }
    fun changeBookStatus(book: Book, status: Status?) {
        var personalBook: PersonalBook? = getPersonalBookByBook(book)
        if (personalBook == null) {
            personalBook = PersonalBook(
                book=book,
                status=status as Status // assuming we arent trying to delete non-existent book
            )
            books.add(personalBook)
            viewModelScope.launch(Dispatchers.IO) {
                personalBookRepository.addPersonalBook(personalBook)
            }
        } else {
            when (status) {
                null -> removeBook(personalBook)
                else -> personalBook.status = status
            }
            viewModelScope.launch(Dispatchers.IO) {
                personalBookRepository.updatePersonalBook(personalBook)
            }
        }
    }

    fun getBooks(status: Status?=null): List<PersonalBook> {
        // null status to allow for getting books of all types
        println("getting all books inner ${books.toString()}")
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

    fun updateReadPages(personalBook: PersonalBook, newPages: Int) {
        // assuming the desired changed property was already changed
        // so the only task is to save it in the database
        personalBook.readPages = newPages
        viewModelScope.launch(Dispatchers.IO) {
            personalBookRepository.updatePersonalBook(personalBook)
        }
    }
}