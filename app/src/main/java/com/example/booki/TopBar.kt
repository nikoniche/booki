package com.example.booki

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookiTopBar() {
    TopAppBar(
        navigationIcon = {
            Icon(
                painter=painterResource(R.drawable.ic_menu_book),
                contentDescription = "app icon",
                tint= Color.White,
                modifier = Modifier
                    .size(35.dp)
                    .padding(start = 4.dp)
            )
        },
        title = {
            Box(
                modifier= Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .border(1.dp, Color.White),
            ) {
                Row(
                    modifier= Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    var testInput by remember {
                        mutableStateOf("")
                    }
                    BasicTextField(
                        value = testInput,
                        onValueChange = {
                            testInput = it
                        },
                        textStyle = TextStyle(color=Color.White, fontSize=16.sp),
                        cursorBrush = SolidColor(Color.White),
                        modifier = Modifier.padding(start=10.dp),
                        decorationBox = {
                            innerTextField ->
                            if (testInput.isEmpty()) {
                                Text(
                                    text="Search any book",
//                                    modifier=Modifier.padding(start=10.dp),
                                    fontSize=16.sp,
                                    color=Color.LightGray,
                                    fontStyle = FontStyle.Italic,
                                )
                            }
                            innerTextField()
                        }
                    )
                    Row {
                        if(testInput.isNotEmpty()) {
                            IconButton(
                                onClick={

                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_search),
                                    contentDescription = "search",
                                    tint=Color.White,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }
                        IconButton(
                            onClick={

                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_scan),
                                contentDescription = "scan isbn",
                                tint=Color.White,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }
            }
        },
        actions = {
            IconButton(
                onClick={}
            ) {
                Icon(
                    painter= painterResource(id = R.drawable.ic_menu),
                    contentDescription = "browse button",
                    tint=Color.White,
                    modifier = Modifier.size(35.dp)
                )
            }
        },
        colors=TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black,
            actionIconContentColor = Color.White,
            navigationIconContentColor = Color.White,
            titleContentColor = Color.White,
        )
    )
}

@Preview(showBackground=true)
@Composable
fun BookiTopBarPreview() {
    BookiTopBar()
}