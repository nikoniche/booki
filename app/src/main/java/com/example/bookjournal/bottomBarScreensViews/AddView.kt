package com.example.bookjournal.bottomBarScreensViews

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import coil.compose.rememberAsyncImagePainter
import com.example.bookjournal.Screen
import com.example.bookjournal.openLibraryAPI.OpenLibraryViewModel
import com.example.bookjournal.books.Book

@Composable
fun AddView(navController: NavHostController) {
    val openLibraryViewModel: OpenLibraryViewModel = viewModel()

    var enteredISBN by remember {
        mutableStateOf("")
    }

    var searchingStatus by remember { mutableStateOf(false) }

    var foundBook by remember {
        mutableStateOf<Book?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Find book by ISBN")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                modifier = Modifier.padding(end=10.dp),
                value = enteredISBN,
                onValueChange = {
                    enteredValue ->
                    enteredISBN = enteredValue
                },
                placeholder = {
                    Text("ISBN", color=Color.LightGray)
                },
                label = {
                    Text("Enter ISBN")
                }
            )
            IconButton(
                modifier = Modifier
                    .offset(y = 5.dp)
                    .wrapContentSize(),
                onClick={
                    searchingStatus = true
                    openLibraryViewModel.getBookByISBN(enteredISBN) {
                        book ->
                        foundBook = book
                        searchingStatus = false
                    }
                }
            ) {
                Icon(
                    imageVector=Icons.Default.Search,
                    contentDescription="Search button",
                    tint=Color.White,
                    modifier= Modifier
                        .fillMaxSize()
                        .background(Color.LightGray)
                        .size(100.dp)
                        .padding(0.dp),
                )
            }
        }
        Divider(Modifier.padding(vertical=20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            if (searchingStatus) {
                Text("Searching for the book..")
            } else {
                if (foundBook == null) {
                    Text("Book not found.")
                } else {
                    Text("Book was found!")

                }
            }
        }

        if (foundBook != null ) {
            FoundBookCard(foundBook as Book) {
                navController.navigate(Screen.BookDetailsScreen.route+"/isbn/${it.isbn}")
            }
        }
    }
}

@Composable
fun FoundBookCard(book: Book, onClick: (Book) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable{ onClick(book) },
        elevation = 2.dp,
    ) {
        Row(
            modifier = Modifier.height(70.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = book.coverUrl),
                contentDescription = "book cover",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(70.dp)
            )
            Column {
                Row {
                    Text(
                        text=book.title,
                        fontWeight= FontWeight.SemiBold,
                        fontSize=16.sp,
                    )
                    Text(
                        text=", ${book.author}",
                        fontWeight= FontWeight.Normal,
                        fontSize=16.sp,
                    )
                }

                Text(
                    text="2024, ${book.numberOfPages} pages",
                    fontWeight = FontWeight.Light,
                    color=Color.Gray,
                    fontSize=12.sp,
                )
            }
        }
    }
}
