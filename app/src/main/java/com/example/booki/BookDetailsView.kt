package com.example.booki

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.booki.books.Book
import com.example.booki.books.PersonalBook
import com.example.booki.books.Status
import com.example.booki.books.dummyPersonalBook
import com.example.booki.openLibraryAPI.OpenLibraryViewModel
import com.example.booki.personalData.PersonalRecordsViewModel

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
        // universal slash public book data
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        ) {
            Image(
                //painter = rememberAsyncImagePainter(model = book.coverUrl),
                painter=painterResource(R.drawable.nineteen_eighty_four),
                contentDescription = "book cover",
                contentScale = ContentScale.Fit,
                alignment = AbsoluteAlignment.CenterLeft,
                modifier = Modifier
                    .width(125.dp)
                    .height(160.dp)
                    .padding(end = 16.dp)
            )

            Column {
                Text(
                    text = "What a really long title i should somehow handle this",
                    fontWeight=FontWeight.Bold,
                    fontSize = 21.sp,
                )
                Text(
                    text=book.author,
                    fontStyle= FontStyle.Italic,
                    fontSize=16.sp,
                )
                Text(
                    text="${book.numberOfPages} pages\n" +
                            "published: ${book.publishDate}\n" +
                            "ISBN-10: ${book.isbn}",
                    fontSize=13.sp,
                    color= Color.Gray
                )
            }
        }

        MyDivider(Modifier.padding(vertical=8.dp))

        // personal data
        Column(
            modifier = Modifier.fillMaxSize()
        )
        {
            var personalBook: PersonalBook? = PersonalRecordsViewModel.getPersonalRecordsForGeneralBook(book)
            val hasBookLogged: Boolean = personalBook != null

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                var dropDownMenuExpandedState by remember {
                    mutableStateOf(false)
                }
                var bookStatusState by remember {
                    mutableStateOf(personalBook?.status ?: Status.NotRead)
                }

                Button(
                    onClick = {
                        dropDownMenuExpandedState = !dropDownMenuExpandedState
                    },
                    colors = ButtonDefaults
                        .buttonColors(
                            containerColor=Color.White,
                            contentColor=Color.Black
                        ),
                    border = BorderStroke(2.5.dp, Color.Black),
                    modifier = Modifier.height(30.dp),
                    shape = ButtonDefaults.filledTonalShape,
                    contentPadding = PaddingValues(horizontal=10.dp, vertical=1.dp),
                )
                {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text=bookStatusState.inText,
                        )
                        Icon(Icons.Default.KeyboardArrowDown, null)
                    }

                    DropdownMenu(
                        modifier = Modifier.wrapContentWidth(),
                        expanded = dropDownMenuExpandedState,
                        onDismissRequest = {
                            dropDownMenuExpandedState = false
                        }) {
                        @Composable
                        fun BookStatusSelection(status: Status) {
                            DropdownMenuItem(
                                text = {
                                    Text(status.inText, color=status.color)
                                },
                                onClick = {
                                    bookStatusState = status
                                    dropDownMenuExpandedState = false
                                }
                            )
                        }
                        BookStatusSelection(Status.NotRead)
                        BookStatusSelection(Status.Reading)
                        BookStatusSelection(Status.Finished)
                        BookStatusSelection(Status.Dropped)
                    }
                }

                if(hasBookLogged) {
                    personalBook as PersonalBook
                    Text(
                        text = "${personalBook.readPages}/${personalBook.book.numberOfPages}",
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                    StarRating(personalBook = personalBook, starSize = 25.dp)
                }
            }

            if(hasBookLogged) {
                personalBook as PersonalBook
                Spacer(Modifier.height(12.dp))
                MyHeadline(text = "Review")
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    label={Text("Review")},
                    placeholder={Text("no review written")},
                    colors=OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor=Color.Black,
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        unfocusedLabelColor = Color.Black,
                        focusedLabelColor = Color.Black,
                    ),
                    value = "",
                    onValueChange = {}
                )

                Spacer(Modifier.height(12.dp))
                MyHeadline(text = "Book notes")
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    label={Text("Book notes")},
                    colors=OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor=Color.Black,
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        unfocusedLabelColor = Color.Black,
                        focusedLabelColor = Color.Black,
                    ),
                    value = "",
                    onValueChange = {}
                )
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