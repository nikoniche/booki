package com.example.booki.personalData

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booki.Book
import com.example.booki.PersonalBook
import com.example.booki.Status
import com.example.booki.personalData.local_database.Graph
import com.example.booki.personalData.local_database.PersonalBookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonalRecordsViewModel: ViewModel() {
    private val personalBookRepository: PersonalBookRepository = Graph.personalBookRepository

    private val _books: MutableState<List<PersonalBook>> = mutableStateOf( emptyList() )
    val books: State<List<PersonalBook>> = _books

    init {
        refreshBooks()
    }

    private fun refreshBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            val listOfSavedBooks = personalBookRepository.getPersonalBooks()
            _books.value = listOfSavedBooks
        }
    }

    fun addBook(personalBook: PersonalBook) {
        viewModelScope.launch(Dispatchers.IO) {
            personalBookRepository.addPersonalBook(personalBook)
            refreshBooks()
        }
    }
    fun updateBook(personalBook: PersonalBook) {
        viewModelScope.launch(Dispatchers.IO) {
            personalBookRepository.updatePersonalBook(personalBook)
            refreshBooks()
        }
    }
    fun removeBook(
        personalBook: PersonalBook
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            personalBookRepository.deletePersonalBook(personalBook)
            refreshBooks()
        }
    }

    fun getPersonalBookByBook(book: Book): PersonalBook? {
        _books.value.forEach {
            personalBook ->
            if (personalBook.book.id == book.id) {
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
                status =status as Status // assuming we arent trying to delete non-existent book
            )
            addBook(personalBook)
        } else {
            when (status) {
                null -> removeBook(personalBook)
                else -> personalBook.status = status
            }
            updateBook(personalBook)
        }
    }

    fun getBooks(status: Status?=null): List<PersonalBook> {
        // null status to allow for getting books of all types
        return if (status == null) _books.value
        else {
            val matchingBooks: MutableList<PersonalBook> = mutableListOf()
            _books.value.forEach {
                    book ->
                if (book.status == status) matchingBooks.add(book)
            }
            matchingBooks
        }
    }
}