package com.example.booki.book_details_views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.booki.MyHeadline
import com.example.booki.PersonalBook
import com.example.booki.personalData.PersonalRecordsViewModel

@Composable
fun EditableBookNotes(
    personalBook: PersonalBook,
    personalRecordsViewModel: PersonalRecordsViewModel,
) {
    var writtenNotes by remember {
        mutableStateOf(personalBook.bookNotes)
    }
    var editedNotes by remember {
        mutableStateOf(false)
    }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MyHeadline(text = "Book notes")
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .focusRequester(focusRequester),
            colors= OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor= Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                focusedLabelColor = Color.Black,
            ),
            value = writtenNotes,
            onValueChange = {
                writtenNotes = it
                editedNotes = true
            }
        )
        if(editedNotes) {
            Row(
                modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(top=8.dp),
                horizontalArrangement = Arrangement.End
            ) {
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
                        personalBook.bookNotes = writtenNotes
                        personalRecordsViewModel.updateBook(personalBook)
                        focusManager.clearFocus()
                        editedNotes = false
                    }
                ) {
                    Text("Save", color= Color.Green, fontSize=16.sp)
                }
            }
        }
    }
}