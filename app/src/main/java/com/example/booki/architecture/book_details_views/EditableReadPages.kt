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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.booki.PersonalBook
import com.example.booki.Status
import com.example.booki.personalData.PersonalRecordsViewModel

@Composable
fun EditableReadPages(
    personalRecordsViewModel: PersonalRecordsViewModel
) {
    val trackedBook: PersonalBook = personalRecordsViewModel.viewedPersonalBook.value!!
    var writtenPages by remember {
        mutableStateOf(trackedBook.readPages.toString())
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        BasicTextField(
            singleLine=true,
            textStyle = TextStyle(
                textAlign= TextAlign.End,
                fontSize = 16.sp,
            ),
            modifier= Modifier
                .width(40.dp)
                .offset(y=0.75.dp)
                .padding(end=2.dp),
            value = writtenPages,
            onValueChange = {
                writtenPages = it
                val formattedPages = it.toIntOrNull()
                if (formattedPages != null) {
                    if (formattedPages <= trackedBook.book.numberOfPages) {
                        trackedBook.readPages = formattedPages
                        if (trackedBook.readPages == trackedBook.book.numberOfPages) {
                            trackedBook.status = Status.Finished
                        }
                        personalRecordsViewModel.updateBook(trackedBook)
                    }
                }
            }
        )

        Text(
            text = "/ ${personalRecordsViewModel.viewedPersonalBook.value!!.book.numberOfPages}",
            color = Color.Black,
            fontSize = 16.sp,
            modifier = Modifier
        )
    }
}
