package com.example.bookjournal

sealed class Screen(val name: String, val route: String) {
    object HomeScreen : Screen(name = "Home", route = "home_screen")
}