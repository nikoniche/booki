package com.example.booki.book_search

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.booki.Book
import com.example.booki.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookManuallyView(
    //personalRecordsViewModel: PersonalRecordsViewModel=viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text="Add book manually",
            fontSize=21.sp,
            fontWeight= FontWeight.Bold,
            modifier=Modifier.padding(bottom=16.dp),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                modifier = Modifier
                    .width(150.dp)
                    .height(200.dp),
                contentPadding= PaddingValues(0.dp),
                shape=RectangleShape,
                colors=ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,

                    ),
                onClick = {
                    // todo select book cover image
                }
            ) {
                Image(
                    painter=painterResource(R.drawable.nineteen_eighty_four), // todo make empty book cover image
                    contentDescription = "select book cover button",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit,
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        @Composable
        fun PropertyTextField(
            propertyName: String,
            //writtenState: MutableState<String>,
            onValueChange: (String) -> Unit,
            placeholderText: String="",
            isAloneInLine: Boolean=true,
        ) {
            val modifier = if(!isAloneInLine) Modifier.weight(1f) else Modifier

            var writtenState by remember {
                mutableStateOf("")
            }

            Column(
                modifier= modifier
                    .padding(vertical = 6.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text=propertyName,
                    fontSize=18.sp,
                    fontWeight= FontWeight.SemiBold,
                )
                TextField(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        unfocusedIndicatorColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                        cursorColor = Color.Black,
                        unfocusedPlaceholderColor=Color.Gray,
                    ),
                    placeholder = {
                        Text(
                            text=placeholderText,

                        )
                    },
                    singleLine = true,
                    textStyle = TextStyle(color=Color.Black, fontSize = 16.sp),
                    value = writtenState,
                    onValueChange = {
                        writtenState = it
                        onValueChange(writtenState)
                    }
                )
            }

        }

        val userBook = Book()
        PropertyTextField(
            propertyName = "Title",
            onValueChange = {
                newValue ->
                userBook.title = newValue
            }
        )
        PropertyTextField(
            propertyName = "Subtitle",
            onValueChange = {
                newValue ->
                userBook.subtitle = newValue
            }
        )
        PropertyTextField(
            propertyName = "Authors",
            placeholderText="seperate authors with a comma (Albert Camus, Franz Kafka)",
            onValueChange = {
                newValue ->
                val namesList: List<String> = newValue.split(",").map {
                    it.trim()
                }
                userBook.authors = namesList
            }
        )

        Row(
            modifier= Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            PropertyTextField(
                propertyName = "Number of pages",
                isAloneInLine = false,
                onValueChange = {
                    newValue ->
                    newValue.trim()
                    val newValueAsInt: Int? = newValue.toIntOrNull()
                    if (newValueAsInt != null) {
                        userBook.numberOfPages = newValueAsInt
                    } else {
                        // new value can not be cast to int
                    }
                }
            )
            Spacer(Modifier.width(16.dp))
            PropertyTextField(
                propertyName = "ISBN",
                isAloneInLine = false,
                onValueChange = {
                    newValue ->
                    val transformedIsbn = newValue
                        .replace("-", "")
                        .replace(" ", "")
                        .trim()
                    when(transformedIsbn.length) {
                        10 -> userBook.isbn10 = transformedIsbn
                        13 -> userBook.isbn13 = transformedIsbn
                        else -> {
                            // isbn isnt correct length
                        }
                    }
                }
            )
        }
        Row(
            modifier= Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            PropertyTextField(
                propertyName = "Publisher",
                isAloneInLine = false,
                onValueChange = {
                    newValue ->
                    userBook.publisher = newValue
                },
            )
            Spacer(Modifier.width(16.dp))
            PropertyTextField(
                propertyName = "Publish date",
                isAloneInLine = false,
                onValueChange = {
                    newValue ->
                    userBook.publishDate = newValue
                }
            )
        }

        /*PropertyTextField(propertyName = "Description")
        PropertyTextField(propertyName = "Genres")*/
        Row(
            modifier= Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 16.dp),
            horizontalArrangement=Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text="you can set your reading data after you create the book",
                color=Color.DarkGray,
                fontStyle= FontStyle.Italic,
                fontSize=14.sp,
                modifier=Modifier.width(200.dp),
            )
            Button(
                colors = ButtonDefaults
                    .buttonColors(
                        containerColor=Color.White,
                        contentColor=Color.Black
                    ),
                border = BorderStroke(2.5.dp, Color.Black),
                modifier = Modifier.wrapContentSize(),
                shape = ButtonDefaults.filledTonalShape,
                contentPadding = PaddingValues(horizontal=12.dp, vertical=0.dp),
                onClick={
                    // todo save the book with source "User"
                }
            ) {
                Text("Add book", color = Color.Black, fontSize = 17.sp)
            }
        }
    }
}

@Preview(showBackground=true)
@Composable
fun AddBookManuallyViewPreview() {
    AddBookManuallyView()
}