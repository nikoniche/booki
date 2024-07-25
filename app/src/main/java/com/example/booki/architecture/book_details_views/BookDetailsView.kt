package com.example.booki.architecture.book_details_views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.booki.Book
import com.example.booki.book_search.SearchViewModel
import com.example.booki.data.PersonalRecordsViewModel

@Composable
fun BookDetailsView(
    bookIsbn: String,
    personalRecordsViewModel: PersonalRecordsViewModel,
    searchViewModel: SearchViewModel,
) {
    if(!searchViewModel.search.value.searching) {
        var foundBook: Book? = null
        searchViewModel.search.value.result.forEach {
            book ->
            if(book.getISBN() == bookIsbn) {
                foundBook = book
            }
        }
        if(foundBook != null) {
            BookView(
                book=foundBook as Book,
                personalRecordsViewModel=personalRecordsViewModel,
            )
        } else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 20.dp),
            )
            {
                Text("Error during search: ${searchViewModel.search.value.error}")
            }
        }
    } else {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp),
        )
        {
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp),
                color = Color.Black,
                backgroundColor = Color.LightGray,
            )
        }
    }
}
