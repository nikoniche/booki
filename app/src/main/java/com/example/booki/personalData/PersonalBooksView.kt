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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.booki.MyAppColumn
import com.example.booki.MyDivider
import com.example.booki.MyHeadline
import com.example.booki.PersonalBook
import com.example.booki.Status

@Composable
fun PersonalBooksView(
    navHostController: NavHostController,
    personalRecordsViewModel: PersonalRecordsViewModel
) {
    MyAppColumn(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        RowOfBooks(
            status = Status.Reading,
            navHostController=navHostController,
            personalRecordsViewModel=personalRecordsViewModel,
        )

        Spacer(Modifier.height(8.dp))
        MyDivider()
        Spacer(Modifier.height(8.dp))

        RowOfBooks(
            status = Status.Finished,
            navHostController=navHostController,
            personalRecordsViewModel=personalRecordsViewModel,
        )

        Spacer(Modifier.height(8.dp))
        MyDivider()
        Spacer(Modifier.height(8.dp))

        RowOfBooks(
            status = Status.PlanToRead,
            navHostController=navHostController,
            personalRecordsViewModel=personalRecordsViewModel,
        )

        Spacer(Modifier.height(8.dp))
        MyDivider()
        Spacer(Modifier.height(8.dp))

        RowOfBooks(
            status = Status.Dropped,
            navHostController=navHostController,
            personalRecordsViewModel=personalRecordsViewModel,
        )
    }
}

@Composable
fun RowOfBooks(
    status: Status,
    navHostController: NavHostController,
    personalRecordsViewModel: PersonalRecordsViewModel,
) {
    MyHeadline(text = status.inText)
    Spacer(Modifier.height(8.dp))

    val books: List<PersonalBook> = personalRecordsViewModel.getBooks(status)
    if (books.isNotEmpty()) {
        LazyRow {
            items(books) {
                    personalBook ->
                when (status) {
                    Status.PlanToRead -> PersonalBookCard(
                        personalBook = personalBook,
                        showPageProgress = true,
                        navHostController=navHostController,
                    )
                    Status.Reading -> PersonalBookCard(
                        personalBook = personalBook,
                        showPageProgress = true,
                        navHostController=navHostController,
                    )
                    Status.Finished -> PersonalBookCard(
                        personalBook = personalBook,
                        showRating = true,
                        navHostController=navHostController,
                    )
                    Status.Dropped -> PersonalBookCard(
                        personalBook = personalBook,
                        showPageProgress = true,
                        showRating = true,
                        navHostController=navHostController,
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
    PersonalBooksView(rememberNavController(), viewModel())
}