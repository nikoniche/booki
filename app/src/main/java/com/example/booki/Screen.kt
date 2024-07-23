package com.example.booki

sealed class Screen(
    val name: String,
    val route: String
) {
    data object HomeScreen : Screen(name = "Home", route = "home_screen")
    data object AddScreen : Screen(name = "Add", route = "add_screen")
    data object AccountScreen : Screen(name = "Account", route = "account_screen")

    data object BookDetailsScreen : Screen(
        name="Book details",
        route="book_details_screen"
    )

    data object PersonalBooksScreen : Screen(
        name="Personal books",
        route="personal_books_screen"
    )

}