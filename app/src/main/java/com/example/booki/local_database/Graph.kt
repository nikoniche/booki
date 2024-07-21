package com.example.booki.local_database

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Drop the old table if it exists
        database.execSQL("DROP TABLE IF EXISTS `personal_books-table`")

        // Create the new table with the updated schema
        database.execSQL("""
            CREATE TABLE `personal_books-table` (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                bookString TEXT NOT NULL,
                statusId INTEGER NOT NULL,
                pageProgress INTEGER NOT NULL,
                review TEXT NOT NULL,
                rating INTEGER NOT NULL
            )
        """)
    }
}

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
        )
            .addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration() // delete before production -> resetting DB on schema change
            .build()
        println("database provided")
    }
}