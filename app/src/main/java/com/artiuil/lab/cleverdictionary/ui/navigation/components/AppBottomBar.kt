package com.artiuil.lab.cleverdictionary.ui.navigation.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.artiuil.lab.cleverdictionary.R

data class BottomBarItem(
    val route: String,
    val icon: ImageVector,
    @StringRes val iconDescRes: Int,
    @StringRes val labelRes: Int
)

@Composable
fun AppBottomBar(navController: NavController, currentRoute: String) {
    if (currentRoute != Screen.CreateSet.route &&
        currentRoute != Screen.ShowSet.route &&
        currentRoute != Screen.Auth.route &&
        currentRoute != Screen.EditSet.route
    ) {
        val bottomBarItems = listOf(
            BottomBarItem(
                route = Screen.Home.route,
                icon = Icons.Default.Home,
                iconDescRes = R.string.home_icon,
                labelRes = R.string.home
            ),
            BottomBarItem(
                route = Screen.Library.route,
                icon = Icons.Default.Menu,
                iconDescRes = R.string.library_icon,
                labelRes = R.string.library
            ),
            BottomBarItem(
                route = Screen.Profile.route,
                icon = Icons.Default.Person,
                iconDescRes = R.string.profile_icon,
                labelRes = R.string.profile
            )
        )

        NavigationBar {
            bottomBarItems.forEach { item ->
                NavigationBarItem(
                    selected = currentRoute == item.route,
                    onClick = {
                        if (currentRoute != item.route) {
                            navController.navigate(item.route) {
                                launchSingleTop = true
                                restoreState = true
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = stringResource(id = item.iconDescRes)
                        )
                    },
                    label = { Text(stringResource(id = item.labelRes)) },
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}
