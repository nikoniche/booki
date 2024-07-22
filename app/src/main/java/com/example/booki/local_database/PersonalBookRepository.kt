package com.example.booki.local_database

import com.example.booki.books.PersonalBook
import kotlinx.coroutines.flow.Flow
import com.example.booki.books.Book
import com.example.booki.books.Status
import com.google.gson.Gson
import kotlinx.coroutines.flow.first

class PersonalBookRepository(
    private val personalBookDao: PersonalBookDao
) {
    private fun convertPersonalBookToEntity(personalBook: PersonalBook): PersonalBookEntity {
        return PersonalBookEntity(
            id=personalBook.id,
            bookString=personalBook.book.textify(),
            statusId=personalBook.status.id,
            pageProgress = personalBook.readPages,
            rating=personalBook.rating,
            review=personalBook.review,
        )
    }

    suspend fun addPersonalBook(personalBook: PersonalBook){
        personalBookDao.addPersonalBook(convertPersonalBookToEntity(personalBook))
    }

    suspend fun getPersonalBooks(): List<PersonalBook> {
        val personalBookEntities = personalBookDao.getAllPersonalBooks().first()
        return personalBookEntities.map { entity ->
            PersonalBook(
                id = entity.id,
                book = Gson().fromJson(entity.bookString, Book::class.java),
                status = when (entity.statusId) {
                    0 -> Status.PlanToRead
                    1 -> Status.Reading
                    2 -> Status.Finished
                    3 -> Status.Dropped
                    else -> Status.PlanToRead
                },
                readPages = entity.pageProgress,
                rating = entity.rating,
                review = entity.review
            )
        }
    }

    fun getPersonalBookEntityById(id: Long): Flow<PersonalBookEntity> {
        return personalBookDao.getPersonalBookById(id)
    }

    suspend fun updatePersonalBook(personalBook: PersonalBook) {
        personalBookDao.updatePersonalBook(convertPersonalBookToEntity(personalBook))
        println("updated books: ${getPersonalBooks()}")
        println("books updated")
    }

    suspend fun deletePersonalBook(personalBook: PersonalBook) {
        personalBookDao.deletePersonalBook(convertPersonalBookToEntity(personalBook))
    }
}