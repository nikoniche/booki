package com.example.booki

import com.example.booki.openLibraryAPI.OpenLibrary

object Bookmaster {
    /*fun getBooksByISBN(isbn: String): List<Book> {*/
        // this function will always return a list of books, no matter the source
        // although there should be only one book with a set ISBN, there can be different sources of the book

        // right now the only source is OpenLibrary
        /*val booksWithMatchingISBN: MutableList<Book> = mutableListOf()
        OpenLibrary.getBookByISBN(
            isbn=isbn
        ) {
            foundBook ->
            if (foundBook != null) {
                println("bookmaster sees book ${foundBook.title}")
                booksWithMatchingISBN.add(foundBook)
            }
        }*/

        /*// todo add locally saved books that match isbn

        return booksWithMatchingISBN*/
    /*}*/
}