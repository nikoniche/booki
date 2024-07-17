package com.example.bookjournal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView() {
    val navController: NavHostController = rememberNavController()
    val navigationManager: NavigationManager = NavigationManager(navController = navController)

    var screenName by remember {
        mutableStateOf("Home")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(R.color.primaryBackgroundColor)),
                title = {
                    Text(
                        text=screenName,
                        style = TextStyle(
                            fontSize=21.sp,
                            fontWeight = FontWeight.Normal,
                            color=colorResource(R.color.primaryContrastColor)
                        )
                    )
                },
                navigationIcon = {
                    if(screenName==Screen.BookDetailsScreen.name) {
                        IconButton(onClick = {
                            navController.navigateUp()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null,
                                tint= Color.Black
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = colorResource(R.color.primaryBackgroundColor),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Screen.ListOfScreens.bottomBarScreens.forEach {
                        screen ->
                        IconButton(
                            onClick = {
                                navigationManager.NavigateToScreen(screen)
                                screenName = screen.name
                            },
                        )
                        {
                            Icon(
                                modifier = Modifier.size(35.dp),
                                painter = painterResource(id = screen.iconId),
                                contentDescription = screen.name,
                                tint=colorResource(R.color.primaryContrastColor)
                            )
                        }
                    }
                }
            }
        }

    ) {
        navigationManager.NavigationView(it)
    }
}
