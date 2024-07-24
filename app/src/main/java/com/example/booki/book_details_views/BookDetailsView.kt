package com.example.booki.book_details_views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.booki.Book
import com.example.booki.openLibraryAPI.OpenLibrary
import com.example.booki.personalData.PersonalRecordsViewModel

@Composable
fun BookDetailsView(
    bookIsbn: String,
    personalRecordsViewModel: PersonalRecordsViewModel,
) {
    var searched: Boolean by remember {
        mutableStateOf(false)
    }
    var loading by remember {
        mutableStateOf(true)
    }
    var foundBook by remember {
        mutableStateOf<Book?>(null)
    }

    if (!searched) {
        OpenLibrary.getBookByISBN(bookIsbn) {
                book ->
            println("book search finished")
            foundBook = book
            searched = true
            loading = false
        }
    }

    if (foundBook != null) {
        val loadedBook: Book = foundBook as Book
        BookView(
            book=loadedBook,
            personalRecordsViewModel=personalRecordsViewModel,
        )
    } else {
        /*Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

        }*/
    }
}
