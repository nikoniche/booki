package com.example.booki.data.local_database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PersonalBookEntity::class],
    version = 3,
    exportSchema = false
)
abstract class PersonalBookDatabase : RoomDatabase() {
    abstract fun personalBookDao(): PersonalBookDao
}