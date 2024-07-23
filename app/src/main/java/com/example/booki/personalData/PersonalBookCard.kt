package com.example.booki.personalData

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.booki.R
import com.example.booki.Screen
import com.example.booki.PersonalBook

@Composable
fun PersonalBookCard(
    personalBook: PersonalBook,
    showPageProgress: Boolean=false,
    showReadingStatus: Boolean=false,
    showRating: Boolean=false,
    navHostController: NavHostController, // for quick accessing book details
)
{
    Box(
        modifier = Modifier
            .width(150.dp)
            .wrapContentHeight()
            .padding(end = 9.dp)
            .clickable {
                navHostController.navigate(Screen.BookDetailsScreen.route + "/isbn/${personalBook.book.getISBN()}")
            },
        //elevation = 4.dp,
        //border= BorderStroke(2.5.dp, Color.Black),
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 10.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = personalBook.book.coverUrl),
                contentDescription = null,
                modifier = Modifier
                    .width(95.dp)
                    .height(90.dp)
            )
            Spacer(Modifier.height(10.dp))

            val textSpacerHeight: Dp = 2.dp
            Text(
                text=personalBook.book.title,
                fontWeight= FontWeight.SemiBold
            )
            Spacer(Modifier.height(textSpacerHeight))
            Text(
                text=personalBook.book.getAuthors(),
                fontWeight= FontWeight.Light,
                fontSize=12.sp
            )

            if(showReadingStatus) {
                Spacer(Modifier.height(textSpacerHeight))
                Text(
                    text=personalBook.status.inText,
                    color=personalBook.status.color,
                )
            }

            if(showPageProgress) {
                Spacer(Modifier.height(textSpacerHeight))
                Text(
                    text="${personalBook.readPages}/${personalBook.book.numberOfPages}",
                    color= Color.Gray,
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
fun StarRating(personalBook: PersonalBook, starSize: Dp =16.dp) {
    val amountOfFullStars: Int = personalBook.rating.div(2)
    val halfStar: Int = personalBook.rating.mod(2)

    Row {
        for (i in 0 until amountOfFullStars) {
            Icon(
                painter= painterResource(id = R.drawable.star),
                contentDescription ="full star",
                tint= Color.Unspecified,
                modifier = Modifier.size(starSize)
            )
        }

        if (halfStar == 1) {
            Icon(
                painter= painterResource(id = R.drawable.half_star),
                contentDescription ="half star",
                tint= Color.Unspecified,
                modifier = Modifier.size(starSize)
            )
        }

        for (i in 0 until 5-amountOfFullStars-halfStar) {
            Icon(
                painter= painterResource(id = R.drawable.empty_star),
                contentDescription ="empty star",
                tint= Color.Unspecified,
                modifier = Modifier.size(starSize)
            )
        }
    }
}