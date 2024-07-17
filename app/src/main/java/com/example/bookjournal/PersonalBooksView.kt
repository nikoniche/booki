package com.example.bookjournal

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookjournal.personalData.PersonalRecordsViewModel

@Composable
fun PersonalBooksView() {
    MyAppColumn(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        MyHeadline(text = "Reading")
        Spacer(Modifier.height(8.dp))
        LazyRow {
            items(PersonalRecordsViewModel.getBooks("Reading")) {
                personalBook ->
                PersonalBookCard(
                    personalBook = personalBook,
                    showPageProgress = true,
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        MyDivider()
        Spacer(Modifier.height(8.dp))

        MyHeadline(text = "Finished")
        Spacer(Modifier.height(8.dp))
        LazyRow {
            items(PersonalRecordsViewModel.getBooks("Finished")) {
                    personalBook ->
                PersonalBookCard(
                    personalBook = personalBook,
                    showRating = true,
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        MyDivider()
        Spacer(Modifier.height(8.dp))

        MyHeadline(text = "Plan to read")
        Spacer(Modifier.height(8.dp))
        LazyRow {
            items(PersonalRecordsViewModel.getBooks("Not read")) {
                personalBook ->
                PersonalBookCard(
                    personalBook = personalBook,
                    showPageProgress = true,
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        MyDivider()
        Spacer(Modifier.height(8.dp))

        MyHeadline(text = "Dropped")
        Spacer(Modifier.height(8.dp))
        LazyRow {
            items(PersonalRecordsViewModel.getBooks("Dropped")) {
                    personalBook ->
                PersonalBookCard(
                    personalBook = personalBook,
                    showPageProgress = true,
                    showRating = true,
                )
            }
        }
    }
}

@Preview(showBackground=true)
@Composable
fun PersonalBooksViewPreview() {
    PersonalBooksView()
}