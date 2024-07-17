package com.example.bookjournal

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.bookjournal.books.Book
import com.example.bookjournal.openLibraryAPI.OpenLibraryViewModel

@Composable
fun BookDetails(bookIsbn: String) {
    val openLibraryViewModel: OpenLibraryViewModel = viewModel()
    var loading by remember { mutableStateOf(true) }
    var foundBook by remember {
        mutableStateOf<Book?>(null)
    }

    openLibraryViewModel.getBookByISBN(bookIsbn) {
        book ->
        foundBook = book
        loading = false
    }

    if (!loading) {
        val loadedBook: Book = foundBook ?: Book()
        BookView(loadedBook)
    } else {
        /*Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

        }*/
    }
}

@Composable
fun BookView(book: Book) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = book.coverUrl),
            contentDescription = "book cover",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(300.dp)
        )
        Spacer(modifier=Modifier.height(20.dp))

        Text(
            text = book.title,
            fontWeight=FontWeight.Bold,
            fontSize = 21.sp,
        )
        Spacer(modifier=Modifier.height(8.dp))
        Text(
            text=book.author,
            fontSize=18.sp,
        )
        Spacer(modifier=Modifier.height(4.dp))
        Text(
            text="published on ${book.publishDate}\n${book.numberOfPages} pages",
            fontSize=14.sp,
            color= Color.Gray
        )
        Row(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("Status: not read")
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ,
                onClick={

                }
            ) {
                Text("Add")
            }
        }
    }
}

@Preview(showBackground=true)
@Composable
fun BookViewPreview() {
    BookView(book = Book(
        title="Testing title",
        author="FirstName Surname",
        numberOfPages = 100,
    ))
}