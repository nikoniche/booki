package com.example.booki.local_database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.booki.books.PersonalBook

@Entity(tableName="personal_books-table")
data class PersonalBookEntity(
    @PrimaryKey(autoGenerate=true)
    val id: Long=0L,

    @ColumnInfo("isbn")
    val isbn: String,
    @ColumnInfo("statusId")
    val statusId: Int,
    @ColumnInfo("pageProgress")
    val pageProgress: Int,
    @ColumnInfo("review")
    val review: String,
//    @ColumnInfo("bookNotes")
//    val bookNotes: String,
    @ColumnInfo("rating")
    val rating: Int,

)