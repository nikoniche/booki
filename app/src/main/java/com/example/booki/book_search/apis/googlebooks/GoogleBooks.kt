package com.example.booki.book_search.apis.googlebooks

import com.example.booki.Book
import com.example.booki.BuildConfig
import kotlinx.coroutines.coroutineScope
import retrofit2.await

object GoogleBooks {
    private val apiKey = BuildConfig.GOOGLE_API_KEY

    suspend fun getBookByISBN(isbn: String): List<Book> = coroutineScope {
        try {
            val response = GoogleBooksClient.googleBooksService.getBookByISBN("isbn:$isbn", apiKey)
            val search = response.await()
            val bookResults: List<Book> = search.items.map {
                val bookData = it.volumeInfo

                Book(
                    title = bookData.title,
                    authors = bookData.authors,
                    description = bookData.description ?: "",
                    numberOfPages = bookData.pageCount ?: 0,
                    coverUrl = bookData.imageLinks?.thumbnail ?: "",
                    language = bookData.language ?: "",
                    publishDate = bookData.publishedDate ?: "",
                    publisher = bookData.publisher ?: "",
                    isbn13 = bookData.industryIdentifiers.first().identifier,
                    isbn10 = bookData.industryIdentifiers[1].identifier,
                )
            }

            return@coroutineScope bookResults
        } catch (e: Exception) {
            println("GOOGLE BOOKS EXCEPTIONS: ${e.message}")
            return@coroutineScope emptyList<Book>()
        }
    }
}