package com.booki.personalData

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booki.Book
import com.booki.PersonalBook
import com.booki.Status
import com.booki.personalData.local_database.Graph
import com.booki.personalData.local_database.PersonalBookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonalRecordsViewModel: ViewModel() {
    private val personalBookRepository: PersonalBookRepository = Graph.personalBookRepository

    private val _books: MutableState<List<com.booki.PersonalBook>> = mutableStateOf( emptyList() )
    val books: State<List<com.booki.PersonalBook>> = _books

    val viewedPersonalBook: MutableState<com.booki.PersonalBook?> = mutableStateOf(null)

    init {
        refreshBooks()
    }

    private fun refreshBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            val listOfSavedBooks = personalBookRepository.getPersonalBooks()
            _books.value = listOfSavedBooks

            // check if viewedPersonalBook still exists
            var foundViewedBook = false
            _books.value.forEach {
                if (it == viewedPersonalBook.value) foundViewedBook = true
            }
            if (!foundViewedBook) {
                viewedPersonalBook.value = null
            }
        }
    }

    fun addBook(personalBook: com.booki.PersonalBook) {
        viewModelScope.launch(Dispatchers.IO) {
            personalBookRepository.addPersonalBook(personalBook)
            refreshBooks()
        }
    }
    fun updateBook(personalBook: com.booki.PersonalBook) {
        viewModelScope.launch(Dispatchers.IO) {
            personalBookRepository.updatePersonalBook(personalBook)
            refreshBooks()
//            viewedPersonalBook.value = personalBook
        }
    }
    fun removeBook(
        personalBook: com.booki.PersonalBook
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            personalBookRepository.deletePersonalBook(personalBook)
            refreshBooks()
        }
    }

    fun setViewedBookByBook(book: com.booki.Book) {
        _books.value.forEach {
            personalBook ->
            if (personalBook.book.getISBN() == book.getISBN()) {
                viewedPersonalBook.value = personalBook
            }
        }
    }

    fun getBooks(status: com.booki.Status?=null): List<com.booki.PersonalBook> {
        // null status to allow for getting books of all types
        return if (status == null) _books.value
        else {
            val matchingBooks: MutableList<com.booki.PersonalBook> = mutableListOf()
            _books.value.forEach {
                    book ->
                if (book.status == status) matchingBooks.add(book)
            }
            matchingBooks
        }
    }
}