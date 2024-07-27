package com.example.booki.book_search.apis.openLibraryAPI

import com.example.booki.Book
import kotlinx.coroutines.coroutineScope

object OpenLibrary {

    suspend fun getBookByISBN(isbn: String): List<Book> = coroutineScope {
        try {
            val response = OpenLibraryClient.openLibraryService.getBookByISBN(isbn)
            if (!response.isSuccessful) {
                println("Error: ${response.code()} - ${response.message()}")
                return@coroutineScope emptyList<Book>()
            }

            val search = response.body()
            if (search?.records == null) {
                println("No result found.")
                return@coroutineScope emptyList<Book>()
            }

            val bookResults: List<Book> = search.records.map { entry ->
                println(entry.value.data.title)
                val bookData = entry.value.data

                val isbn10 = bookData.identifiers.isbn_10?.firstOrNull() ?: ""
                val isbn13 = bookData.identifiers.isbn_13?.firstOrNull() ?: ""
                val coverUrl = bookData.cover?.large ?: "https://lgimages.s3.amazonaws.com/nc-md.gif"
                val subtitle = bookData.excerpts?.firstOrNull()?.text ?: ""

                Book(
                    title = bookData.title,
                    authors = bookData.authors.map { it.name },
                    numberOfPages = bookData.number_of_pages,
                    publishDate = bookData.publish_date,
                    isbn10 = isbn10,
                    isbn13 = isbn13,
                    coverUrl = coverUrl,
                    subtitle = subtitle,
                    publisher = bookData.publishers.firstOrNull()?.name ?: "",
                    genres = bookData.subjects.map { it.name },
                    source = "OpenLibrary",
                )
            }

            return@coroutineScope bookResults
        } catch (e: Exception) {

            return@coroutineScope emptyList<Book>()
        }
    }
}