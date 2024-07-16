package com.example.bookjournal

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.drawablepainter.rememberDrawablePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView() {
    val navController: NavHostController = rememberNavController()
    val navigationManager: NavigationManager = NavigationManager(navController = navController)

    var currentScreen by remember {
        mutableStateOf<Screen.BottomBarScreen>(Screen.BottomBarScreen.HomeScreen)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(R.color.menuColor)),
                title = {
                    Text(
                        text=currentScreen.name,
                        style = TextStyle(
                            fontSize=21.sp,
                            fontWeight = FontWeight.Normal,
                        )
                    )
                },
                navigationIcon = {
                    /*Icon(
                        painterResource(id = currentScreen.iconId), currentScreen.name,
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .size(30.dp))
*/
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = colorResource(R.color.menuColor),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Screen.ListOfScreens.bottomBarScreens.forEach {
                        screen ->
                        IconButton(
                            onClick = {
                                navigationManager.NavigateToScreen(screen)
                                currentScreen = screen
                            },
                        )
                        {
                            Icon(
                                modifier = Modifier.size(35.dp),
                                painter = painterResource(id = screen.iconId),
                                contentDescription = screen.name
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
