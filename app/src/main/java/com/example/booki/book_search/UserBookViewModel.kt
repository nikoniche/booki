package com.example.booki.book_search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booki.Book
import com.example.booki.personalData.local_database.Graph
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserBookViewModel: ViewModel() {
    private val userBookRepository = Graph.userBookRepository

    private val _userBooks: MutableState<List<Book>> = mutableStateOf(emptyList())
    val userBooks: State<List<Book>> = _userBooks

    private fun fetchSavedBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            _userBooks.value = userBookRepository.getUserBooks()
        }
    }

    init {
        fetchSavedBooks()
    }

    fun addBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            userBookRepository.addUserBook(book)
            fetchSavedBooks()
        }
    }

    fun updateBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            userBookRepository.updateUserBook(book)
            fetchSavedBooks()
        }
    }

    fun deleteBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            userBookRepository.deleteUserBook(book)
            fetchSavedBooks()
        }
    }
}