package com.example.booki

sealed class Screen(val name: String, val route: String) {

    object ListOfScreens {
        val bottomBarScreens = listOf(BottomBarScreen.HomeScreen, BottomBarScreen.AddScreen, BottomBarScreen.AccountScreen)
    }
    sealed class BottomBarScreen(name: String, route: String, val iconId: Int) : Screen(name, route) {
        data object HomeScreen : BottomBarScreen(name = "Home", route = "home_screen", iconId = R.drawable.ic_home)
        data object AddScreen : BottomBarScreen(name = "Add", route = "add_screen", iconId = R.drawable.ic_add)
        data object AccountScreen : BottomBarScreen(name = "Account", route = "account_screen", iconId = R.drawable.ic_account)
    }

    data object BookDetailsScreen : Screen(
        name="Book details",
        route="book_details_screen"
    )

    data object PersonalBooksScreen : Screen(
        name="Personal books",
        route="personal_books_screen"
    )

}