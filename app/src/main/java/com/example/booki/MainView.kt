package com.example.booki

import android.content.pm.PackageManager
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Divider
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.booki.Screen.BookDetailsScreen
import com.example.booki.personalData.PersonalRecordsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView() {
    val navController: NavHostController = rememberNavController()
    val personalRecordsViewModel: PersonalRecordsViewModel = viewModel()
    val navigationManager = NavigationManager(
        navHostController = navController,
        personalRecordsViewModel=personalRecordsViewModel,
    )

    var currentScreen by remember {
        mutableStateOf<Screen>(Screen.HomeScreen)
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black),
                title = {
                    Text(
                        text=currentScreen.name,
                        style = TextStyle(
                            fontSize=21.sp,
                            fontWeight = FontWeight.Normal,
                            color=Color.White,
                        )
                    )
                },
                navigationIcon = {
//                        IconButton(onClick = {
//                            navController.navigateUp()
//                        }) {
//                            Icon(
//                                imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null,
//                                tint= Color.Black
//                            )
//                        }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Black,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    listOf(Screen.HomeScreen, Screen.AddScreen, Screen.AccountScreen).forEach {
                        screen ->
                        IconButton(
                            onClick = {
                                navigationManager.NavigateToScreen(screen)
                                currentScreen = screen
                            },
                        )
                        {
                            val tint =
                                if (currentRoute == screen.route)
                                    Color.White
                                else
                                    Color.LightGray
                            Icon(
                                modifier = Modifier.size(35.dp),
                                painter = painterResource(id = when(screen) {
                                    Screen.HomeScreen -> R.drawable.ic_home
                                    Screen.AddScreen -> R.drawable.ic_add
                                    Screen.AccountScreen -> R.drawable.ic_account
                                    else -> R.drawable.empty_star
                                }),
                                contentDescription = screen.name,
                                tint=tint
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

@Preview(showBackground=true)
@Composable
fun Prw() {
    MainView()
}