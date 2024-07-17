package com.example.bookjournal

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.bookjournal.bottomBarScreensViews.AccountView
import com.example.bookjournal.bottomBarScreensViews.AddView
import com.example.bookjournal.bottomBarScreensViews.HomeView

class NavigationManager(
    private val navHostController: NavHostController
) {
    @Composable
    fun NavigationView(padding: PaddingValues) {
        NavHost(
            modifier = Modifier.padding(padding),
            navController = navHostController,
            startDestination = Screen.BottomBarScreen.HomeScreen.route)
        {
            // bottom bar screens
            composable(Screen.BottomBarScreen.HomeScreen.route) {
                HomeView(navHostController)
            }
            composable(Screen.BottomBarScreen.AddScreen.route) {
                AddView(navController=navHostController)
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

            composable(Screen.PersonalBooksScreen.route) {
                PersonalBooksView()
            }
        }
    }
    fun NavigateToScreen(screen: Screen) {
        navHostController.navigate(screen.route)
    }
}
