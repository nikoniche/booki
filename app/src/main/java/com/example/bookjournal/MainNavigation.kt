package com.example.bookjournal

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bookjournal.bottomBarScreensViews.AccountView
import com.example.bookjournal.bottomBarScreensViews.AddView
import com.example.bookjournal.bottomBarScreensViews.HomeView

class NavigationManager(
    private val navController: NavHostController
) {
    @Composable
    fun NavigationView(padding: PaddingValues) {
        NavHost(
            modifier = Modifier.padding(padding),
            navController = navController,
            startDestination = Screen.BottomBarScreen.HomeScreen.route)
        {
            // bottom bar screens
            composable(Screen.BottomBarScreen.HomeScreen.route) {
                HomeView()
            }
            composable(Screen.BottomBarScreen.AddScreen.route) {
                AddView()
            }
            composable(Screen.BottomBarScreen.AccountScreen.route) {
                AccountView()
            }
        }
    }
    fun NavigateToScreen(screen: Screen) {
        navController.navigate(screen.route)
    }
}
