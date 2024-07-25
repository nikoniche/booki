package com.example.booki

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booki.openLibraryAPI.OpenLibrary
import kotlinx.coroutines.launch
import java.lang.IndexOutOfBoundsException

class SearchViewModel(

) : ViewModel() {
    data class Search(
        var searching: Boolean = true,
        var result: List<Book> = emptyList(),
        var error: String="",
    )

    private val _search = mutableStateOf(Search())
    val search: State<Search> = _search

    fun fetchSearchResults(query: String) {
        // notify the UI that the search is ongoing
        _search.value = Search()

        // determining whether or not the query was an ISBN
        val queryAsIsbnString = query.replace("-", "").replace(" ", "").trim()
        var searchByISBN = false
        if (queryAsIsbnString.length == 10 || queryAsIsbnString.length == 13) {
            if (queryAsIsbnString.toLongOrNull() != null) {
                searchByISBN = true
            }
        }

        if (searchByISBN) {
            // search by isbn
            viewModelScope.launch {
                try {
                    println("triggering")
                    _search.value = _search.value.copy(
                        result=OpenLibrary.getBookByISBN(isbn = queryAsIsbnString),
                    )
                } catch (e: Exception) {
                    // failed
                    _search.value = _search.value.copy(
                        error="error: ${e.toString()}, message: ${e.message ?: "null message"}",
                    )
                } finally {
                    _search.value = _search.value.copy(
                        searching=false,
                    )
                }
            } /*else {
            // search by matching words
            val individualQueryWords = query.split(" ").map {
                it.trim()
            }
            *//* todo pridat do bookmaster funkci, ktera zavoli funkci OpenLibrary, ale taky by mela vratit list *//*
        }*/
        }
    }
}