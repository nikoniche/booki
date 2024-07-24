package com.example.booki

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.booki.personalData.PersonalRecordsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView() {
    val navHostController: NavHostController = rememberNavController()
    val personalRecordsViewModel: PersonalRecordsViewModel = viewModel()
    val searchViewModel: SearchViewModel = viewModel()
    val navigationManager = NavigationManager(
        navHostController = navHostController,

        personalRecordsViewModel=personalRecordsViewModel,
        searchViewModel=searchViewModel,
    )

    var currentScreen by remember {
        mutableStateOf<Screen>(Screen.HomeScreen)
    }

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            BookiTopBar(
                searchViewModel=searchViewModel,
                navHostController=navHostController,
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