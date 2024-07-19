package com.example.booki.openLibraryAPI

import OpenLibraryResponse
import androidx.lifecycle.ViewModel
import com.example.booki.books.Book
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class OpenLibraryViewModel : ViewModel() {
    fun getBookByISBN(isbn: String, onResult: (Book?) -> Unit) {
        val call = OpenLibraryClient.openLibraryService.getBookByISBN(isbn)

        call.enqueue(object : Callback<OpenLibraryResponse> {
            override fun onResponse(call: Call<OpenLibraryResponse>, response: Response<OpenLibraryResponse>) {
                if (response.isSuccessful) {
                    val post = response.body()
                    println("Book was found.")
                    if (post != null) {
                        post.records.entries.forEach {
                            entry ->
                            val bookData = entry.value.data
                            val book: Book
                            println(bookData.identifiers)



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
                                coverUrl = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.freepik.com%2Ficon%2Ferror-404_2034479&psig=AOvVaw2yyjqkFKmQtKhiRloV4gk8&ust=1721484791462000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCPDljOaks4cDFQAAAAAdAAAAABAJ"
                            }

                            book = Book(
                                title = bookData.title,
                                author = bookData.authors[0].name,
                                numberOfPages = bookData.number_of_pages,
                                publishDate = bookData.publish_date,
                                isbn10 = isbn10,
                                isbn13 = isbn13,
                                coverUrl = coverUrl,
                            )

                            onResult(book)
                        }
                    }
                    // Handle the retrieved post data
                } else {
                    println("Failed")
                    // Handle error
                    try {
                        val errorBody = response.errorBody()?.string()
                        println("Error Code: ${response.code()}")
                        println("Error Message: ${errorBody}")
                    } catch (e: IOException) {
                        e.printStackTrace()
                        onResult(null)
                    }
                }
            }

            override fun onFailure(call: Call<OpenLibraryResponse>, t: Throwable) {
                println("Handling failure")
                println(t.message)
                onResult(null)
                // Handle failure
            }
        })
    }
}