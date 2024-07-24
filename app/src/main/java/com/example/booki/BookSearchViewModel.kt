package com.example.booki

import androidx.lifecycle.ViewModel

class BookSearchViewModel(

) : ViewModel() {
    fun searchBook(query: String) {
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
        } else {
            // search by matching words
            val individualQueryWords = query.split(" ").map {
                it.trim()
            }

        }
    }
}