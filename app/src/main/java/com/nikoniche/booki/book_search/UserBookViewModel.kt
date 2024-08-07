package com.nikoniche.booki.book_search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikoniche.booki.Book
import com.nikoniche.booki.personalData.local_database.Graph
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserBookViewModel: ViewModel() {
    private val userBookRepository = Graph.userBookRepository

    private val _userBooks: MutableState<List<com.nikoniche.booki.Book>> = mutableStateOf(emptyList())
    val userBooks: State<List<com.nikoniche.booki.Book>> = _userBooks

    private val _userBookToEdit: MutableState<com.nikoniche.booki.Book?> = mutableStateOf(null)
    val userBookToEdit: State<com.nikoniche.booki.Book?> = _userBookToEdit

    private fun fetchSavedBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            _userBooks.value = userBookRepository.getUserBooks()

            // check if userBookToEdit still exists
            var foundEditBook = false
            _userBooks.value.forEach {
                if (it == _userBookToEdit.value) foundEditBook = true
            }
            if (!foundEditBook) {
                _userBookToEdit.value = null
            }
        }
    }

    init {
        fetchSavedBooks()
    }

    fun addBook(book: com.nikoniche.booki.Book) {
        viewModelScope.launch(Dispatchers.IO) {
            userBookRepository.addUserBook(book)
            fetchSavedBooks()
        }
    }

    fun updateBook(book: com.nikoniche.booki.Book) {
        viewModelScope.launch(Dispatchers.IO) {
            userBookRepository.updateUserBook(book)
            fetchSavedBooks()
        }
    }

    fun deleteBook(book: com.nikoniche.booki.Book) {
        viewModelScope.launch(Dispatchers.IO) {
            userBookRepository.deleteUserBook(book)
            fetchSavedBooks()
        }
    }

    fun triggerBookEdit(userBook: com.nikoniche.booki.Book) {
        _userBookToEdit.value = userBook
    }
}