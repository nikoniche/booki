package com.example.booki.architecture.book_details_views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.booki.MyDivider
import com.example.booki.Book
import com.example.booki.PersonalBook
import com.example.booki.Status
import com.example.booki.personalData.PersonalRecordsViewModel

@Composable
fun BookView(
    book: Book,
    personalRecordsViewModel: PersonalRecordsViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // universal slash public book data
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = book.coverUrl),
//                painter=painterResource(R.drawable.nineteen_eighty_four),
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
                    text =book.title,
                    fontWeight= FontWeight.Bold,
                    fontSize = 21.sp,
                )
                if (book.subtitle != "") {
                    Text(
                        text=book.subtitle,
                        fontWeight=FontWeight.Normal,
                        fontSize =17.sp,
                    )
                }
                Text(
                    text=book.getAuthors(),
                    fontStyle= FontStyle.Italic,
                    fontSize=16.sp,
                )
                Text(
                    text="${book.numberOfPages} pages\n" +
                            "published: ${book.publishDate}\n" +
                            "ISBN: ${book.getISBN() ?: "missing isbn"}",
                    fontSize=13.sp,
                    color= Color.Gray
                )
                Text(
                    text="publisher: ${book.publisher}",
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
            val personalBook: PersonalBook? = personalRecordsViewModel.getPersonalBookByBook(book)

            var bookStatusState by remember {
                mutableStateOf(personalBook?.status) // null status state means
                // that its not added to users personal books
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                var dropDownMenuExpandedState by remember {
                    mutableStateOf(false)
                }

                Button(
                    onClick = {
                        dropDownMenuExpandedState = !dropDownMenuExpandedState
                    },
                    colors = ButtonDefaults
                        .buttonColors(
                            containerColor= Color.White,
                            contentColor= Color.Black
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
                            text=bookStatusState?.inText ?: "Not read",
                            color=bookStatusState?.color ?: Color.Black,
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
                                    personalRecordsViewModel.changeBookStatus(book, status)
                                }
                            )
                        }
                        BookStatusSelection(Status.PlanToRead)
                        BookStatusSelection(Status.Reading)
                        BookStatusSelection(Status.Finished)
                        BookStatusSelection(Status.Dropped)
                    }
                }

                if(bookStatusState == Status.Reading || bookStatusState == Status.Finished || bookStatusState == Status.Dropped) {
                    EditableReadPages(
                        personalBook = personalBook as PersonalBook,
                        personalRecordsViewModel = personalRecordsViewModel,
                    )
                }

                if(bookStatusState != null) {
                    IconButton(
                        onClick={
                            personalRecordsViewModel.removeBook(personalBook as PersonalBook)
                            bookStatusState = null
                        }
                    ) {
                        Icon(Icons.Default.Delete, "delete button")
                    }
                }
            }

            if(bookStatusState == Status.Finished || bookStatusState == Status.Dropped) {
                EditableReview(
                    personalBook = personalBook as PersonalBook,
                    personalRecordsViewModel = personalRecordsViewModel
                )
            }

            if(bookStatusState == Status.Reading || bookStatusState == Status.Finished) {
                Spacer(Modifier.height(12.dp))
                EditableBookNotes(
                    personalBook = personalBook as PersonalBook,
                    personalRecordsViewModel = personalRecordsViewModel,
                )
            }
        }
    }
}
