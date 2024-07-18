package com.example.booki

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.booki.books.PersonalBook
import com.example.booki.books.dummyPersonalBook

@Composable
fun MyHeadline(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text=text,
        fontSize=23.sp,
        fontWeight= FontWeight.Bold,
        modifier = modifier,
    )
}

@Composable
fun MyDivider(modifier: Modifier = Modifier) {
    Divider(color=Color.Black, modifier = modifier)
}

@Composable
fun MyAppColumn(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp),
        content = content
    )
}

@Composable
fun PersonalBookCard(
    personalBook: PersonalBook,
    showPageProgress: Boolean=false,
    showReadingStatus: Boolean=false,
    showRating: Boolean=false,
)
{
    Card(
        modifier = Modifier
            .wrapContentSize()
            .padding(end = 9.dp),
        elevation = 4.dp,
        border= BorderStroke(2.5.dp, Color.Black),
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.nineteen_eighty_four),
                contentDescription = null,
                modifier = Modifier
                    .width(95.dp)
                    .height(90.dp)
            )
            Spacer(Modifier.height(10.dp))

            val textSpacerHeight: Dp = 2.dp
            Text(
                text=personalBook.book.title,
                fontWeight=FontWeight.SemiBold
            )
            Spacer(Modifier.height(textSpacerHeight))
            Text(
                text=personalBook.book.author,
                fontWeight=FontWeight.Light,
                fontSize=12.sp
            )

            if(showReadingStatus) {
                Spacer(Modifier.height(textSpacerHeight))
                val statusColor = when(personalBook.status) {
                    "Reading" -> Color.Yellow
                    "Finished" -> Color.Green
                    "Dropped" -> Color.Red
                    "Not read" -> Color.DarkGray
                    else -> {
                        Color.Transparent
                    }
                }
                Text(
                    text=personalBook.status,
                    color=statusColor,
                )
            }

            if(showPageProgress) {
                Spacer(Modifier.height(textSpacerHeight))
                Text(
                    text="${personalBook.readPages}/${personalBook.book.numberOfPages}",
                    color=Color.Gray,
                    fontSize=10.sp
                )
            }

            if (showRating) {
                Spacer(Modifier.height(textSpacerHeight))
                StarRating(personalBook, 16.dp)
            }
        }
    }
}

@Composable
fun StarRating(personalBook: PersonalBook, starSize: Dp=16.dp) {
    val amountOfFullStars: Int = personalBook.rating.div(2)
    val halfStar: Int = personalBook.rating.mod(2)

    Row {
        for (i in 0 until amountOfFullStars) {
            Icon(
                painter= painterResource(id = R.drawable.star),
                contentDescription ="full star",
                tint=Color.Unspecified,
                modifier = Modifier.size(starSize)
            )
        }

        if (halfStar == 1) {
            Icon(
                painter= painterResource(id = R.drawable.half_star),
                contentDescription ="half star",
                tint=Color.Unspecified,
                modifier = Modifier.size(starSize)
            )
        }

        for (i in 0 until 5-amountOfFullStars-halfStar) {
            Icon(
                painter= painterResource(id = R.drawable.empty_star),
                contentDescription ="empty star",
                tint=Color.Unspecified,
                modifier = Modifier.size(starSize)
            )
        }
    }
}

@Preview(showBackground=true)
@Composable
fun PersonalBookCardPreview() {
    PersonalBookCard(personalBook = dummyPersonalBook, showRating = true)
}