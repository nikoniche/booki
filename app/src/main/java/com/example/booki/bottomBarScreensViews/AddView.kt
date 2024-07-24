package com.example.booki.bottomBarScreensViews

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.booki.MyAppColumn
import com.example.booki.MyDivider
import com.example.booki.MyHeadline
import com.example.booki.R
import com.example.booki.Screen
import com.example.booki.openLibraryAPI.OpenLibrary
import com.example.booki.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddView(navController: NavHostController) {
    var enteredISBN by remember {
        mutableStateOf("9781785042188")
    }

    var searchingStatus by remember { mutableStateOf(false) }

    var foundBook by remember {
        mutableStateOf<Book?>(null)
    }

    MyAppColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        MyHeadline(text = "Find book by ISBN")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
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
                    OpenLibrary.getBookByISBN(enteredISBN) {
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (foundBook != null ) {
                FoundBookCard(foundBook as Book) {
                    navController.navigate(Screen.BookDetailsScreen.route+"/isbn/${it.getISBN()}")
                }
            } else {
                if (searchingStatus) {
                    Text("Searching for the book..")
                } else {
                    Text("Book was not found.")
                }
            }
        }

        MyDivider(modifier = Modifier.padding(vertical=12.dp))

        MyHeadline(text = "Add a book manually")
        Spacer(Modifier.height(16.dp))

        @Composable
        fun EntryField(labelText: String, value: String, modifier: Modifier=Modifier, onValueChanged: (String) -> Unit) {
            OutlinedTextField(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                label={Text(labelText)},
                placeholder={Text(labelText)},
                colors=OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor=Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedLabelColor = Color.Black,
                ),
                value = value,
                onValueChange = onValueChanged
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Image(
                //painter = rememberAsyncImagePainter(model = personalBook.book.coverUrl),
                painter= painterResource(R.drawable.nineteen_eighty_four),
                contentDescription = "book cover",
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center,
                modifier = Modifier
                    .width(125.dp)
                    .wrapContentHeight()
                    .padding(16.dp)
            )

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(0.dp)
            ) {
                var titleState by remember {
                    mutableStateOf("")
                }
                EntryField(
                    labelText = "Title",
                    value = titleState
                ) {
                    titleState = it
                }

                var authorState by remember {
                    mutableStateOf("")
                }
                EntryField(
                    labelText = "Author",
                    value = authorState
                ) {
                    authorState = it
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            var numberOfPagesState by remember {
                mutableStateOf("")
            }
            EntryField(
                modifier = Modifier.weight(1f).padding(end=5.dp),
                labelText = "Number of pages",
                value = numberOfPagesState
            ) {
                numberOfPagesState = it
            }

            var isbnState by remember {
                mutableStateOf("")
            }
            EntryField(
                modifier = Modifier.weight(1f).padding(start=5.dp),
                labelText = "ISBN",
                value = isbnState
            ) {
                isbnState = it
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
            .clickable { onClick(book) },
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
                        text=", ${book.getAuthors()}",
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

@Preview(showBackground=true)
@Composable
fun ShowPreview() {
    AddView(navController = rememberNavController())
}
