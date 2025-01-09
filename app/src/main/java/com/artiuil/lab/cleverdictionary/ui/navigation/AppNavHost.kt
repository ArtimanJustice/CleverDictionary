package com.artiuil.lab.cleverdictionary.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.artiuil.lab.cleverdictionary.ui.screens.home.HomeScreen
import com.artiuil.lab.cleverdictionary.ui.screens.library.LibraryScreen
import com.artiuil.lab.cleverdictionary.ui.screens.profile.ProfileScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.HOME,
        modifier = modifier
    ) {
        composable(NavigationRoute.HOME.route) { HomeScreen() }
        composable(NavigationRoute.LIBRARY.route) { LibraryScreen() }
        composable(NavigationRoute.PROFILE.route) { ProfileScreen() }
    }
}