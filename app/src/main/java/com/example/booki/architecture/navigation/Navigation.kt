package com.example.booki.architecture.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.booki.book_search.AddBookManuallyView
import com.example.booki.book_search.SearchResultsView
import com.example.booki.book_search.SearchViewModel
import com.example.booki.architecture.book_details_views.BookDetailsView
import com.example.booki.architecture.menuViews.AccountView
import com.example.booki.architecture.menuViews.HomeView
import com.example.booki.architecture.menuViews.PersonalBooksView
import com.example.booki.data.PersonalRecordsViewModel

class NavigationManager(
    private val navHostController: NavHostController,

    // view models
    private val personalRecordsViewModel: PersonalRecordsViewModel,
    private val searchViewModel: SearchViewModel,
) {
    @Composable
    fun NavigationView(padding: PaddingValues) {
        NavHost(
            modifier = Modifier.padding(padding),
            navController = navHostController,
            startDestination = Screen.HomeScreen.route)
        {
            // bottom bar screens
            composable(Screen.HomeScreen.route) {
                HomeView(
                    navHostController=navHostController,
                    personalRecordsViewModel=personalRecordsViewModel,
                    searchViewModel=searchViewModel,
                )
            }
            /*composable(Screen.AddScreen.route) {
                AddView(navController=navHostController)
            }*/
            composable(Screen.AccountScreen.route) {
                AccountView()
            }

            composable(
                route= Screen.BookDetailsScreen.route + "/isbn/{bookIsbn}",
                arguments = listOf(
                    navArgument("bookIsbn") {
                        type = NavType.StringType
                    }
                )
            ) {
                entry ->
                val bookIsbn: String? = if(entry.arguments != null) entry.arguments!!.getString("bookIsbn") else ""
                BookDetailsView(
                    bookIsbn = bookIsbn?: "",
                    personalRecordsViewModel=personalRecordsViewModel,
                    searchViewModel=searchViewModel,
                )
            }

            composable(Screen.PersonalBooksScreen.route) {
                PersonalBooksView(
                    navHostController=navHostController,
                    personalRecordsViewModel=personalRecordsViewModel,
                    searchViewModel = searchViewModel,
                )
            }
            composable(Screen.AddBookManuallyScreen.route) {
                AddBookManuallyView()
            }
            composable(Screen.SearchResultsScreen.route) {
                SearchResultsView(
                    searchViewModel=searchViewModel,
                    navHostController=navHostController,
                )
            }
        }
    }
    fun NavigateToScreen(screen: Screen) {
        navHostController.navigate(screen.route)
    }
}
