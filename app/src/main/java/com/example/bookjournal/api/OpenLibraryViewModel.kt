package com.example.bookjournal.api

import OpenLibraryResponse
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.bookjournal.books.Book
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class OpenLibraryViewModel : ViewModel() {
    fun getBookByISBN(isbn: String, onResult: (Book?) -> Unit) {
        val call = OpenLibraryClient.openLibraryService.getBookByISBN(isbn)
        var book: Book? = null

        call.enqueue(object : Callback<OpenLibraryResponse> {
            override fun onResponse(call: Call<OpenLibraryResponse>, response: Response<OpenLibraryResponse>) {
                if (response.isSuccessful) {
                    val post = response.body()
                    println("Success")
                    if (post != null) {
                        post.records.entries.forEach {
                            entry ->
                            val bookData = entry.value.data

                            book = Book(
                                title = bookData.title,
                                author = bookData.authors[0].name,
                                numberOfPages = bookData.number_of_pages,
                                publishDate = bookData.publish_date,
                                coverUrl = bookData.cover.large,
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