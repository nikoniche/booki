package com.example.booki.architecture.book_details_views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.booki.PersonalBook
import com.example.booki.data.PersonalRecordsViewModel

@Composable
fun EditableReadPages(
    personalBook: PersonalBook,
    personalRecordsViewModel: PersonalRecordsViewModel
) {
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
                    containerColor= Color.White,
                    contentColor= Color.Black
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
            Text("Save", color= Color.Green, fontSize=16.sp)
        }
    }
}