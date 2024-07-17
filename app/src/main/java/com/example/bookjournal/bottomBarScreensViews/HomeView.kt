package com.example.bookjournal.bottomBarScreensViews

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.Divider
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
import com.example.bookjournal.R
import com.example.bookjournal.books.PersonalBook
import com.example.bookjournal.books.dummyPersonalBooks

@Composable
fun HomeView() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        ReadInLast("Books read this year", 27)
        Spacer(Modifier.height(6.dp))
        ReadInLast("Books read this month", 27)

        Spacer(Modifier.height(12.dp))
        Divider(color=Color.Black)
        Spacer(Modifier.height(12.dp))

        Text(
            text="Your books",
            fontSize=23.sp,
            fontWeight= FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        LazyRow {
            items(dummyPersonalBooks) {
                personalBook ->
                BookCard(personalBook)
            }
        }
    }
}

@Composable
fun ReadInLast(timeFrame: String, amount: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(3.dp, color = Color.Black, shape = RoundedCornerShape(25))
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
        ) {
        Text(
            text=timeFrame,
            fontSize=17.sp,
            fontWeight= FontWeight.Normal,
            modifier = Modifier.padding(horizontal=12.dp)
        )
        Text(
            text="$amount",
            fontSize=20.sp,
            fontWeight= FontWeight.Bold,
            modifier = Modifier.padding(horizontal=12.dp)
        )
    }


}

@Composable
fun BookCard(personalBook: PersonalBook) {
    Card(
        modifier = Modifier
            .wrapContentSize()
            .padding(end=6.dp),
        elevation = 4.dp,
        border= BorderStroke(2.5.dp, Color.Black),
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(horizontal=6.dp, vertical=12.dp)
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
            Spacer(Modifier.height(textSpacerHeight))
            Text(
                text=personalBook.status,
                color=statusColor,
            )
            Spacer(Modifier.height(textSpacerHeight))
            Text(
                text="${personalBook.readPages}/${personalBook.book.numberOfPages}",
                color=Color.Gray,
                fontSize=10.sp
            )

        }
    }
}

@Preview(showBackground=true)
@Composable
fun HomeViewPreview() {
    HomeView()
}