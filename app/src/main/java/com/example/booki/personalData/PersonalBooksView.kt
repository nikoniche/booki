package com.example.booki.personalData

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.booki.MyAppColumn
import com.example.booki.MyDivider
import com.example.booki.MyHeadline
import com.example.booki.PersonalBookCard
import com.example.booki.books.PersonalBook
import com.example.booki.books.Status

@Composable
fun PersonalBooksView() {
    MyAppColumn(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        RowOfBooks(status = Status.Reading)

        Spacer(Modifier.height(8.dp))
        MyDivider()
        Spacer(Modifier.height(8.dp))

        RowOfBooks(status = Status.Finished)

        Spacer(Modifier.height(8.dp))
        MyDivider()
        Spacer(Modifier.height(8.dp))

        RowOfBooks(status = Status.NotRead)

        Spacer(Modifier.height(8.dp))
        MyDivider()
        Spacer(Modifier.height(8.dp))

        RowOfBooks(status = Status.Dropped)
    }
}

@Composable
fun RowOfBooks(status: Status) {
    MyHeadline(text = status.inText)
    Spacer(Modifier.height(8.dp))

    val books: List<PersonalBook> = PersonalRecordsViewModel.getBooks(status)
    if (books.isNotEmpty()) {
        LazyRow {
            items(books) {
                    personalBook ->
                when (status) {
                    Status.NotRead -> PersonalBookCard(
                        personalBook = personalBook,
                        showPageProgress = true,
                    )
                    Status.Reading -> PersonalBookCard(
                        personalBook = personalBook,
                        showPageProgress = true,
                    )
                    Status.Finished -> PersonalBookCard(
                        personalBook = personalBook,
                        showRating = true,
                    )
                    Status.Dropped -> PersonalBookCard(
                        personalBook = personalBook,
                        showPageProgress = true,
                        showRating = true,
                    )
                }
            }
        }
    } else {
        Text("You have no books here.")
    }

}
@Preview(showBackground=true)
@Composable
fun PersonalBooksViewPreview() {
    PersonalBooksView()
}