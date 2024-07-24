package com.example.booki.openLibraryAPI

import OpenLibraryResponse
import com.example.booki.Book
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

object OpenLibrary {
    fun getBookByISBN(isbn: String, onResult: (Book?) -> Unit) {
        val call = OpenLibraryClient.openLibraryService.getBookByISBN(isbn)

        call.enqueue(object : Callback<OpenLibraryResponse> {
            override fun onResponse(call: Call<OpenLibraryResponse>, response: Response<OpenLibraryResponse>) {
                if (response.isSuccessful) {
                    val post = response.body()

                    println("Book was found.")

                    if (post != null) {
                        post.records.entries.forEach { entry ->
                            val bookData = entry.value.data

                            lateinit var isbn10: String
                            if (bookData.identifiers.isbn_10 != null) {
                                isbn10 = bookData.identifiers.isbn_10.firstOrNull() ?: ""
                            } else {
                                isbn10 = ""
                            }
                            lateinit var isbn13: String
                            if (bookData.identifiers.isbn_13 != null) {
                                isbn13 = bookData.identifiers.isbn_13.firstOrNull() ?: ""
                            } else {
                                isbn13 = ""
                            }
                            lateinit var coverUrl: String
                            if (bookData.cover != null) {
                                coverUrl = bookData.cover.large
                            } else {
                                coverUrl =
                                    "https://developer.valvesoftware.com/w/images/thumb/b/ba/CSGOErrorTextures.png/200px-CSGOErrorTextures.png"
                            }
                            lateinit var subtitle: String
                            if (bookData.excerpts != null) {
                                subtitle = bookData.excerpts[0].text
                            } else {
                                subtitle = ""
                            }

                            val book = Book(
                                title = bookData.title,
                                authors = bookData.authors.map { it.name },
                                numberOfPages = bookData.number_of_pages,
                                publishDate = bookData.publish_date,
                                isbn10 = isbn10,
                                isbn13 = isbn13,
                                coverUrl = coverUrl,

                                subtitle = subtitle,
                                publisher = bookData.publishers[0].name,
                                genres = bookData.subjects.map {
                                    it.name
                                },
                                source="OpenLibrary",
                            )

                            onResult(book)
                        }
                    }

                    // Handle the retrieved post data
                } else {
                    println("Book not found")
                    // Handle error
                    try {
                        val errorBody = response.errorBody()?.string()
                        println("Error Code: ${response.code()}")
                        println("Error Message: ${errorBody}")
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } finally {
                        onResult(null)
                    }
                }
            }

            override fun onFailure(call: Call<OpenLibraryResponse>, t: Throwable) {
                println("Handling failure")
                println(t.cause)
                println(t.message)
                onResult(null)
                // Handle failure
            }
        })
    }
}