package com.example.booki

import androidx.annotation.Px
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.booki.books.Book
import com.example.booki.books.PersonalBook
import com.example.booki.books.Status
import com.example.booki.openLibraryAPI.OpenLibraryViewModel
import com.example.booki.personalData.PersonalRecordsViewModel
import kotlinx.coroutines.launch

@Composable
fun BookDetails(
    bookIsbn: String,
    personalRecordsViewModel: PersonalRecordsViewModel,
) {
    val openLibraryViewModel: OpenLibraryViewModel = viewModel()

    var searched: Boolean by remember {
        mutableStateOf(false)
    }
    var loading by remember {
        mutableStateOf(true)
    }
    var foundBook by remember {
        mutableStateOf<Book?>(null)
    }

    if (!searched) {
        openLibraryViewModel.getBookByISBN(bookIsbn) {
            book ->
            println("book search finished")
            foundBook = book
            searched = true
            loading = false
        }
    }

    if (foundBook != null) {
        val loadedBook: Book = foundBook as Book
        BookView(
            book=loadedBook,
            personalRecordsViewModel=personalRecordsViewModel,
        )
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
fun BookView(
    book: Book,
    personalRecordsViewModel: PersonalRecordsViewModel,
) {
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
                            "ISBN: ${book.getISBN() ?: "missing isbn"}",
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
                    personalBook as PersonalBook
                    var pageProgressState by remember {
                        mutableStateOf(personalBook.readPages.toString())
                    }
                    var valueChanged by remember {
                        mutableStateOf(false)
                    }
                    val focusRequester = remember { FocusRequester() }
                    val focusManager = LocalFocusManager.current

                    BasicTextField(
                        singleLine=true,
                        textStyle = TextStyle(
                            textAlign= TextAlign.End,
                            fontSize = 16.sp,
                        ),
                        modifier= Modifier
                            .width(30.dp)
                            .offset(y = 1.dp)
                            .focusRequester(focusRequester),
                        value = pageProgressState,
                        onValueChange = {
                            pageProgressState = it
                            valueChanged = true
                        }
                    )

                    Text(
                        text = "/${personalBook.book.numberOfPages}",
                        color = Color.Black,
                        fontSize = 16.sp
                    )

                    if (valueChanged) {
                        Button(
                            colors = ButtonDefaults
                                .buttonColors(
                                    containerColor=Color.White,
                                    contentColor=Color.Black
                                ),
                            border = BorderStroke(2.5.dp, Color.Green),
                            modifier = Modifier
                                .height(30.dp)
                                .padding(horizontal = 12.dp),
                            shape = ButtonDefaults.filledTonalShape,
                            contentPadding = PaddingValues(horizontal=10.dp, vertical=1.dp),
                            onClick={
                                personalBook.readPages = pageProgressState.toInt()
                                personalRecordsViewModel.updateBook(personalBook)
                                println("new pages: ${personalBook.readPages}")
                                focusManager.clearFocus()
                                valueChanged = false
                            }
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .wrapContentWidth(),
                            ) {

                            }
                            Text("Save", color=Color.Green, fontSize=16.sp)
                        }
                    }

                    if(bookStatusState != Status.Reading) {

                        val starSize: Dp = 30.dp
                        var newRating by remember {
                            mutableIntStateOf(personalBook.rating)
                        }

                        @Composable
                        fun HalfClickIconButton(
                            order: Int,
                            icon: @Composable () -> Unit,
                        ) {
                            val density = LocalDensity.current.density
                            Box(
                                modifier = Modifier
                                    .size(starSize)
                                    .border(2.dp, Color.Gray)
                                    .pointerInput(this) {
                                        detectTapGestures {
                                            offset: Offset ->

                                            val sizePx = starSize.toPx()
                                            val clickPosition = offset.x / density
                                            println("offset ${offset.x} vs size ${this.size.width / 2}")
                                            newRating = order * 2
                                            if (offset.x < sizePx / 2) {
                                                newRating -= 1
                                            } else {
                                                // right side
                                            }

                                            personalBook.rating = newRating
                                            personalRecordsViewModel.updateBook(personalBook)
                                            println("Clicked order: $order -> newRating: $newRating")
                                        }

                                    }
                            ) {
                                println(order)
                                icon()
                                Text(order.toString())
                            }
                        }

                        val amountOfFullStars: Int = newRating.div(2)
                        val halfStar: Int = newRating.mod(2)

                        Row {
                            println("generating row for rating: $newRating")
                            println("amount of full stars: $amountOfFullStars")
                            println("half star: $halfStar")
                            for (i in 0 until amountOfFullStars) {
                                HalfClickIconButton(
                                    order=i+1,
                                    icon = {
                                        Icon(
                                            painter= painterResource(id = R.drawable.star),
                                            contentDescription ="full star",
                                            tint= Color.Unspecified,
                                            modifier = Modifier.size(starSize)
                                        )
                                    }
                                )
                            }

                            if (halfStar == 1) {
                                HalfClickIconButton(
                                    order=amountOfFullStars + 1,
                                    icon = {
                                        Icon(
                                            painter= painterResource(id = R.drawable.half_star),
                                            contentDescription ="half star",
                                            tint= Color.Unspecified,
                                            modifier = Modifier.size(starSize)
                                        )
                                    }
                                )
                            }

                            for (i in 0 until 5-amountOfFullStars-halfStar) {
                                HalfClickIconButton(
                                    order=amountOfFullStars + halfStar + i + 1,
                                    icon = {
                                        Icon(
                                            painter= painterResource(id = R.drawable.empty_star),
                                            contentDescription ="empty star",
                                            tint= Color.Unspecified,
                                            modifier = Modifier.size(starSize)
                                        )
                                    }
                                )
                            }
                        }
                    }
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
                personalBook as PersonalBook
                Spacer(Modifier.height(12.dp))
                MyHeadline(text = "Review")
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    label = { Text("Review") },
                    placeholder = { Text("no review written") },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        unfocusedLabelColor = Color.Black,
                        focusedLabelColor = Color.Black,
                    ),
                    value = "",
                    onValueChange = {}
                )
            }

            /*if(bookStatusState == Status.Reading || bookStatusState == Status.Finished) {
                Spacer(Modifier.height(12.dp))
                MyHeadline(text = "Book notes")
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
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
            }*/
        }
    }
}

