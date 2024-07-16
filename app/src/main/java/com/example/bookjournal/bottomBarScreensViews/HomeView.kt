package com.example.bookjournal.bottomBarScreensViews

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookjournal.R
import com.example.bookjournal.books.Book

@Composable
fun HomeView() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Reading")
        LazyRow {

        }
    }
}

@Composable
fun BookCard(book: Book) {
    Card(
        modifier = Modifier.width(150.dp).height(150.dp),
        elevation = 3.dp,

    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.wrapContentSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_home),
                contentDescription = null,
                modifier = Modifier.width(100.dp).height(70.dp)
            )
            Spacer(Modifier.height(10.dp))
            Text(book.title)
            Text(book.author)
        }
    }
}

@Preview(showBackground=true)
@Composable
fun HomeViewPreview() {
    HomeView()
}