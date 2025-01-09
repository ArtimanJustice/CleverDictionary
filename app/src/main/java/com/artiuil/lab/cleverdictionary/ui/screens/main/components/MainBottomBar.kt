package com.artiuil.lab.cleverdictionary.ui.screens.main.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.artiuil.lab.cleverdictionary.ui.navigation.NavigationRoute
import com.artiuil.lab.cleverdictionary.ui.navigation.BottomBarItem
import com.artiuil.lab.cleverdictionary.ui.theme.AppTheme

@Composable
fun MainBottomBar(navController: NavController) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route
    val items = listOf(
        BottomBarItem(
            NavigationRoute.HOME.route,
            Icons.Default.Home,
            NavigationRoute.HOME.label
        ),
        BottomBarItem(
            NavigationRoute.LIBRARY.route,
            Icons.Default.Create,
            NavigationRoute.LIBRARY.label
        ),
        BottomBarItem(
            NavigationRoute.PROFILE.route,
            Icons.Default.AccountBox,
            NavigationRoute.PROFILE.label
        )
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainBottomBarPreview() {
    val fakeNavController = rememberNavController().apply {
        navigate(NavigationRoute.HOME.route)
    }
    AppTheme {
        MainBottomBar(navController = fakeNavController)
    }
}
