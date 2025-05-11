package com.artiuil.lab.cleverdictionary.ui.navigation.components

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.artiuil.lab.cleverdictionary.ui.screens.auth.AuthScreen
import com.artiuil.lab.cleverdictionary.ui.screens.home.HomeScreen
import com.artiuil.lab.cleverdictionary.ui.screens.library.createset.CreateSetScreen
import com.artiuil.lab.cleverdictionary.ui.screens.library.editset.EditSetScreen
import com.artiuil.lab.cleverdictionary.ui.screens.library.setlist.LibraryScreen
import com.artiuil.lab.cleverdictionary.ui.screens.profile.ProfileScreen
import com.artiuil.lab.cleverdictionary.ui.screens.showset.ShowSetScreen

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Screen.Auth.route,
) {
    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            modifier = modifier
        ) {
            composable(Screen.Auth.route) { AuthScreen() }
            composable(Screen.Home.route) { HomeScreen() }
            composable(Screen.Profile.route) { ProfileScreen() }
            composable(Screen.CreateSet.route) { CreateSetScreen() }
            composable(Screen.Library.route) { LibraryScreen() }
            composable(Screen.ShowSet.route) { ShowSetScreen() }
            composable(Screen.EditSet.route) { EditSetScreen() }
        }
    }
}