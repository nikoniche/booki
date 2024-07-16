package com.example.bookjournal

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home

sealed class Screen(val name: String, val route: String) {

    object ListOfScreens {
        val bottomBarScreens = listOf(BottomBarScreen.HomeScreen, BottomBarScreen.AddScreen, BottomBarScreen.AccountScreen)
    }
    sealed class BottomBarScreen(name: String, route: String, val iconId: Int) : Screen(name, route) {
        object HomeScreen : BottomBarScreen(name = "Home", route = "home_screen", iconId = R.drawable.ic_home)
        object AddScreen : BottomBarScreen(name = "Add", route = "add_screen", iconId = R.drawable.ic_add)
        object AccountScreen : BottomBarScreen(name = "Account", route = "account_screen", iconId = R.drawable.ic_account)
    }

}