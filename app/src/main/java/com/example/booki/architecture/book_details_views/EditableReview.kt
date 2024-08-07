package com.example.booki.architecture.book_details_views

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.booki.MyHeadline
import com.example.booki.R
import com.example.booki.PersonalBook
import com.example.booki.personalData.PersonalRecordsViewModel

@Composable
fun EditableReview(
    personalBook: PersonalBook,
    personalRecordsViewModel: PersonalRecordsViewModel
) {
    var writtenReview by remember {
        mutableStateOf(personalBook.review)
    }

    Spacer(Modifier.height(12.dp))
    MyHeadline(text = "Review")
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            focusedLabelColor = Color.Black,
        ),
        value = writtenReview,
        onValueChange = {
            writtenReview = it
            personalBook.review = writtenReview
            personalRecordsViewModel.updateBook(personalBook)
        }
    )

    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.padding(top=8.dp).fillMaxWidth().wrapContentHeight()
    ) {
        EditableRating(
            personalBook=personalBook,
            personalRecordsViewModel=personalRecordsViewModel
        )
    }
}

@Composable
fun EditableRating(
    personalBook: PersonalBook,
    personalRecordsViewModel: PersonalRecordsViewModel
) {
    val starSize: Dp = 30.dp
    var newRating by remember {
        mutableIntStateOf(personalBook.rating)
    }

    @Composable
    fun HalfClickIconButton(
        order: Int,
        icon: @Composable () -> Unit,
    ) {
        Box(
            modifier = Modifier
                .size(starSize)
                .pointerInput(Unit) {
                    detectTapGestures {
                            offset: Offset ->

                        val sizePx = starSize.toPx()
                        newRating = order * 2
                        if (offset.x < sizePx / 2) {
                            newRating -= 1
                        } else {
                            // right side
                        }

                        personalBook.rating = newRating
                        personalRecordsViewModel.updateBook(personalBook)
                    }

                }
        ) {
            icon()
        }
    }

    val amountOfFullStars: Int = newRating.div(2)
    val halfStar: Int = newRating.mod(2)

    Row {
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