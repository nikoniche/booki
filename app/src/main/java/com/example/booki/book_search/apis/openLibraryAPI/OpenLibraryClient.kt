package com.example.booki.book_search.apis.openLibraryAPI

import OpenLibraryService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private object RetrofitClient {
    private const val BASE_URL = "https://openlibrary.org/api/volumes/brief/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object OpenLibraryClient {
    val openLibraryService: OpenLibraryService by lazy {
        RetrofitClient.retrofit.create(OpenLibraryService::class.java)
    }
}