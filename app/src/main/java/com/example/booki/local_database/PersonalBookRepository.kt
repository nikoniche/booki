package com.example.booki.local_database

import com.example.booki.books.PersonalBook
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.forEach
import com.example.booki.books.Book
import com.example.booki.books.Status
import com.google.gson.Gson
import kotlinx.coroutines.flow.first

class PersonalBookRepository(
    private val personalBookDao: PersonalBookDao
) {
    private fun convertPersonalBookToEntity(personalBook: PersonalBook): PersonalBookEntity {
        return PersonalBookEntity(
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
        val flow: Flow<List<PersonalBookEntity>> = personalBookDao.getAllPersonalBooks()
        val list: MutableList<PersonalBook> = mutableListOf()

        val personalBookEntities = flow.first() // Collects the first emission
        // !! PREVENTS THE COLLECT TO RUN INDEFINITELY !! DONT REMOVE

        list.addAll(personalBookEntities.map { entity ->
            PersonalBook(
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
        })

        return list
    }

    fun getPersonalBookEntityById(id: Long): Flow<PersonalBookEntity> {
        return personalBookDao.getPersonalBookById(id)
    }

    suspend fun updatePersonalBook(personalBook: PersonalBook) {
        personalBookDao.updatePersonalBook(convertPersonalBookToEntity(personalBook))
    }

    suspend fun deletePersonalBook(personalBook: PersonalBook) {
        personalBookDao.deletePersonalBook(convertPersonalBookToEntity(personalBook))
    }
}