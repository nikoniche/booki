package com.example.booki.local_database

import android.content.Context
import androidx.room.Room

object Graph {
    lateinit var database: PersonalBookDatabase

    val personalBookRepository by lazy {
        PersonalBookRepository(personalBookDao = database.personalBookDao())
    }

    fun provide(context: Context) {
        database = Room.databaseBuilder(
            context = context,
            klass = PersonalBookDatabase::class.java,
            name = "personal_books.db",
        ).build()
        println("database provided")
    }
}