package com.example.bookjournal

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
                AddView(navController=navController)
            }
            composable(Screen.BottomBarScreen.AccountScreen.route) {
                AccountView()
            }

            composable(
                route=Screen.BookDetailsScreen.route + "/isbn/{bookIsbn}",
                arguments = listOf(
                    navArgument("bookIsbn") {
                        type = NavType.StringType
                    }
                )
            ) {
                entry ->
                val bookIsbn: String? = if(entry.arguments != null) entry.arguments!!.getString("bookIsbn") else ""
                BookDetails(bookIsbn = bookIsbn?: "")
            }
        }
    }
    fun NavigateToScreen(screen: Screen) {
        navController.navigate(screen.route)
    }
}
