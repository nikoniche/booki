package com.example.booki.architecture.menuViews

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.booki.MyHeadline
import com.example.booki.R
import com.example.booki.book_search.UserBookViewModel

@Composable
fun BooksCreatedByMeView(
    userBookViewModel: UserBookViewModel,
) {
    Column(
        modifier = Modifier.padding(8.dp),
    ) {
        MyHeadline(
            text = "Books created by me",
        )
        Spacer(Modifier.height(16.dp))
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            columns = GridCells.Fixed(2),
        ) {
            items(userBookViewModel.userBooks.value) {
                userBook ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier.clickable {
                        println("deleting ${userBook.title}")
                        userBookViewModel.deleteBook(userBook)
                    },
                ) {
                    Image(
                        painter= painterResource(id = R.drawable.picture_of_dorian_gray),
                        contentDescription = "book title cover",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.width(85.dp),
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text=userBook.title,
                        fontSize=24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(text=userBook.id.toString())

                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}
