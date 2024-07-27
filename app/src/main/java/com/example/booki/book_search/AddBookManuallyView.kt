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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.booki.Book
import com.example.booki.R
import com.example.booki.architecture.navigation.Screen

data class TextFieldState(
    val writtenState: MutableState<String> = mutableStateOf(""),
    val onErrorMessage: MutableState<String> = mutableStateOf(""),
    val validityCheck: (TextFieldState) -> Boolean,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyTextField(
    propertyName: String,
    textFieldState: TextFieldState,
    modifier: Modifier=Modifier,
    placeholderText: String="",
) {
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

        val borderColor =
            if(textFieldState.onErrorMessage.value != "") {
                Color.Red
            } else {
                Color.Black
            }

        if(textFieldState.onErrorMessage.value != "") {
            Text(
                text = textFieldState.onErrorMessage.value,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        TextField(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,

                cursorColor = Color.Black,
                unfocusedPlaceholderColor=Color.Gray,

                focusedIndicatorColor = Color.Black,
                focusedTextColor = Color.Black,

                unfocusedIndicatorColor = borderColor,
                unfocusedTextColor = borderColor,
            ),
            placeholder = {
                Text(
                    text=placeholderText,
                )
            },
            singleLine = true,
            textStyle = TextStyle(color=Color.Black, fontSize = 16.sp),
            value = textFieldState.writtenState.value,
            onValueChange = {
                textFieldState.writtenState.value = it
            }
        )
    }

}

@Composable
fun AddBookManuallyView(
    navHostController: NavHostController,
    searchViewModel: SearchViewModel,
    userBookViewModel: UserBookViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        val pageTitle: String =
            if (userBookViewModel.userBookToEdit.value == null) "Add book manually"
            else "Edit: ${userBookViewModel.userBookToEdit.value!!.title}"
        Text(
            text=pageTitle,
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
                    painter=painterResource(R.drawable.select_cover_image), // todo make empty book cover image
                    contentDescription = "select book cover button",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit,
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        val titleState = TextFieldState() {
            if (it.writtenState.value == "") {
                it.onErrorMessage.value = "Field can't be empty"
                false
            } else true
        }
        val subtitleState = TextFieldState() {
            true
        }
        val authorsState = TextFieldState() {
            true
        }
        val numberOfPagesState = TextFieldState() {
            val asInt = it.writtenState.value.toIntOrNull()
            if (asInt == null) {
                it.onErrorMessage.value = "Contains invalid characters"
                false
            } else if (asInt < 1) {
                it.onErrorMessage.value = "Needs to be more than 0"
                false
            } else {
                true
            }
        }
        val isbnState = TextFieldState() {
            val trimmedIsbn = it.writtenState.value.replace("-", "").replace(" ", "").trim()
            if (trimmedIsbn.length != 10 && trimmedIsbn.length != 13) {
                it.onErrorMessage.value = "Not the correct length"
                false
            }
            else {
                if(trimmedIsbn.toLongOrNull() == null) {
                    it.onErrorMessage.value = "Contains invalid characters"
                    false
                } else {
                    true
                }
            }
        }
        val publisherState = TextFieldState() {
            true
        }
        val publishDateState = TextFieldState() {
            // todo try to do some form of a date check, but otherwise field is optional
            true
        }

        val listOfTextFieldStates = listOf(
            titleState, subtitleState, authorsState,
            numberOfPagesState, isbnState, publisherState,
            publishDateState
        )

        // if editing book
        if (userBookViewModel.userBookToEdit.value != null) {
            titleState.writtenState.value = userBookViewModel.userBookToEdit.value!!.title
            subtitleState.writtenState.value = userBookViewModel.userBookToEdit.value!!.subtitle
            authorsState.writtenState.value = userBookViewModel.userBookToEdit.value!!.getAuthors()
            numberOfPagesState.writtenState.value = userBookViewModel.userBookToEdit.value!!.numberOfPages.toString()
            isbnState.writtenState.value = userBookViewModel.userBookToEdit.value!!.getISBN()
            publisherState.writtenState.value = userBookViewModel.userBookToEdit.value!!.publisher
            publishDateState.writtenState.value = userBookViewModel.userBookToEdit.value!!.publishDate
        }

        PropertyTextField(
            propertyName = "Title",
            textFieldState = titleState,
        )
        PropertyTextField(
            propertyName = "Subtitle",
            textFieldState = subtitleState,
        )
        PropertyTextField(
            propertyName = "Authors",
            placeholderText="seperate authors with a comma (Albert Camus, Franz Kafka)",
            textFieldState = authorsState,
        )

        Row(
            modifier= Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            PropertyTextField(
                propertyName = "Number of pages",
                modifier = Modifier.weight(1f),
                textFieldState = numberOfPagesState,
            )
            Spacer(Modifier.width(16.dp))
            PropertyTextField(
                propertyName = "ISBN",
                modifier = Modifier.weight(1f),
                textFieldState = isbnState,
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
                modifier=Modifier.weight(1f),
                textFieldState = publisherState,
            )
            Spacer(Modifier.width(16.dp))
            PropertyTextField(
                propertyName = "Publish date",
                modifier=Modifier.weight(1f),
                textFieldState = publishDateState,
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
                    var allFieldsPassedChecks = true
                    listOfTextFieldStates.forEach {
                        if (!it.validityCheck(it)) {
                            allFieldsPassedChecks = false
                        } else {
                            it.onErrorMessage.value = ""
                        }
                    }

                    if (allFieldsPassedChecks) {
                        val trimmedIsbn = isbnState.writtenState.value.replace("-", "").replace(" ", "").trim()
                        var isbnType: String? = null
                        when (trimmedIsbn.length) {
                            10 -> isbnType = "isbn10"
                            13 -> isbnType = "isbn13"
                        }

                        val id =
                            if (userBookViewModel.userBookToEdit.value == null) -1L
                            else userBookViewModel.userBookToEdit.value!!.id

                        val userBook = Book(
                            id=id,
                            title=titleState.writtenState.value,
                            subtitle=subtitleState.writtenState.value,
                            authors=authorsState.writtenState.value.split(",").map {
                                it.trim()
                            },
                            numberOfPages = numberOfPagesState.writtenState.value.toIntOrNull() ?: -2,
                            isbn10 = if (isbnType == "isbn10") trimmedIsbn else "",
                            isbn13 = if (isbnType == "isbn13") trimmedIsbn else "",
                            publishDate = publishDateState.writtenState.value,
                            publisher = publisherState.writtenState.value,

                            source="User",
                        )

                        if (userBookViewModel.userBookToEdit.value == null) {
                            userBookViewModel.addBook(userBook)
                        } else {
                            userBookViewModel.updateBook(userBook)
                        }

                        searchViewModel.fetchSearchResults(userBook.getISBN() ?: "")
                        navHostController.navigate(
                            Screen.BookDetailsScreen.route + "/isbn/${userBook.getISBN()}"
                        )
                    }
                }
            ) {
                val buttonText: String =
                    if(userBookViewModel.userBookToEdit.value == null) "Add book"
                    else "Edit book"
                Text(
                    text=buttonText,
                    color = Color.Black,
                    fontSize = 17.sp
                )
            }
        }
    }
}
