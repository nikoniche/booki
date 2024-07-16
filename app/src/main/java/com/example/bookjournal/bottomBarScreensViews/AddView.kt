package com.example.bookjournal.bottomBarScreensViews

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.bookjournal.R
import com.example.bookjournal.api.OpenLibraryViewModel
import com.example.bookjournal.books.Book

@Composable
fun AddView() {
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
            Text("Book found: ")
            if (searchingStatus) {
                Icon(Icons.Default.Refresh, contentDescription = null)
            } else {
                if (foundBook == null) {
                    Icon(Icons.Default.Close, contentDescription = null)
                } else {
                    Icon(Icons.Default.Check, contentDescription = null)
                }
            }
        }

        if (foundBook != null ) {
            BookDetails(book = foundBook as Book)
        }
    }
}

@Composable
fun BookDetails(book: Book) {
    Box {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.Left
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = book.coverUrl),
                contentDescription = "book cover",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(150.dp) // Adjust size as needed
            )

            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text("Title: ${book.title}")
                Text("Author: ${book.author}")
                Text("Number of pages: ${book.numberOfPages}")
                Text("Published date: ${book.publishDate}")
            }
        }
    }
}

@Preview(showBackground=true)
@Composable
fun AddViewPreview() {
    BookDetails(Book("test", "test author", 0, "boi do i know"))
}