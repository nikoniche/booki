package com.example.booki

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.booki.openLibraryAPI.OpenLibrary

class SearchViewModel(

) : ViewModel() {
    data class Search(
        var searching: Boolean=false,
        var result: List<Book> = emptyList(),
    )

    private val _search = mutableStateOf(Search())
    val search: State<Search> = _search

    fun fetchSearchResults(query: String) {
        // notify the UI that the search is ongoing
        _search.value = Search()
        _search.value.searching = true

        // determining whether or not the query was an ISBN
        val queryAsIsbnString = query.replace("-", "").replace(" ", "").trim()
        var searchByISBN = false
        if (queryAsIsbnString.length == 10 || queryAsIsbnString.length == 13) {
            if (queryAsIsbnString.toLongOrNull() != null) {
                println("inputted an isbn")
                searchByISBN = true
            }
        }

        if(searchByISBN) {
            // search by isbn
            println("search by isbn triggered")
            //_search.value.result = Bookmaster.getBooksByISBN(queryAsIsbnString)
            OpenLibrary.getBookByISBN(queryAsIsbnString) {
                if (it != null) {
                    // todo would be cool to someohow use the bookmaster
                    // problem is that the code doesnt wait for the openlibrary function to finish
                    // and its weird to have 2 pasted onFinished functions-but it would work
                    _search.value.result = listOf(it)
                    println("found book: ${_search.value.result}")
                }
                _search.value.searching = false
                println(search.value.toString())
            }

            println("search finished")
        } else {
            // search by matching words
            val individualQueryWords = query.split(" ").map {
                it.trim()
            }
            /* todo pridat do bookmaster funkci, ktera zavoli funkci OpenLibrary, ale taky by mela vratit list */
        }
    }
}