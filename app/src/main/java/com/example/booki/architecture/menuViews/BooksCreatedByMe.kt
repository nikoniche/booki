package com.example.booki.architecture.menuViews

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.booki.MyHeadline
import com.example.booki.R
import com.example.booki.architecture.navigation.Screen
import com.example.booki.book_search.SearchViewModel
import com.example.booki.book_search.UserBookViewModel

@Composable
fun BooksCreatedByMeView(
    navHostController: NavHostController,

    searchViewModel: SearchViewModel,
    userBookViewModel: UserBookViewModel,
) {
    Column(
        modifier = Modifier.padding(8.dp),
    ) {
        MyHeadline(
            text = "Books created by me",
        )
        Spacer(Modifier.height(16.dp))

        if (userBookViewModel.userBooks.value.isNotEmpty()) {
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
                            val bookIsbn = userBook.getISBN()
                            searchViewModel.fetchUserBook(userBook)
                            navHostController.navigate(Screen.BookDetailsScreen.route + "/isbn/$bookIsbn")
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
                        Spacer(Modifier.height(16.dp))
                    }
                }
            }
        } else {
            Text(
                text="You have not created any books.",
            )
        }
    }
}
