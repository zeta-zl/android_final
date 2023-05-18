package com.zl.version10.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zl.version10.pages.mainpage.MainPage
import com.zl.version10.pages.mainpage.contactslist.PersonList
import com.zl.version10.pages.user.User
import com.zl.version10.pages.user.detalis.InformationScreen
import com.zl.version10.pages.user.detalis.SettingAbout
import com.zl.version10.pages.user.detalis.SettingSetting

@Composable
fun NavContent() {
    val navHostController = rememberNavController()
    //startDestination:指定默认进入的页面
    NavHost(navController = navHostController, startDestination = "main") {
        composable("main") {
            uploadDataBase()
            MainPage(navHostController)
        }
        composable("User") {
            uploadDataBase()
            User(navHostController)
        }

        composable("PersonList") {
            uploadDataBase()
            PersonList(navHostController)
        }

        composable("Summarize") {
            uploadDataBase()
            InformationScreen(navHostController)
        }
        composable("Setting") {
            uploadDataBase()
            SettingSetting(navHostController)
        }
        composable("About") {
            uploadDataBase()
            SettingAbout(navHostController)
        }
    }
}


@Composable
fun BottomNavigationBar(navController : NavHostController) {
    val items = listOf(
        NavigationItem(
            "Main",
            Icons.Filled.Home,
            "main"
        ),
        NavigationItem(
            "User",
            Icons.Filled.Person,
            "User"
        )
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = Color.White,
//        elevation = 50.dp
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                modifier = Modifier
                    .padding(5.dp),
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(item.route) {
                            inclusive = true
                        }
                    }
                },
                icon = {
                    Icon(item.icon, contentDescription = item.title)
                },
                label = {
                    Text(item.title)
                }
            )
        }
    }
}

data class NavigationItem(
    val title : String,
    val icon : ImageVector,
    val route : String
)


