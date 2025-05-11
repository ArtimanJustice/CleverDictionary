package com.artiuil.lab.cleverdictionary.ui.navigation

import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.artiuil.lab.cleverdictionary.ui.navigation.components.AppBottomBar
import com.artiuil.lab.cleverdictionary.ui.navigation.components.AppTopBar
import com.artiuil.lab.cleverdictionary.ui.navigation.components.LocalSnackbarManager
import com.artiuil.lab.cleverdictionary.ui.navigation.components.LocalTopBarConfig
import com.artiuil.lab.cleverdictionary.ui.navigation.components.NavGraph
import com.artiuil.lab.cleverdictionary.ui.navigation.components.Screen
import com.artiuil.lab.cleverdictionary.ui.navigation.components.SnackbarManager
import com.artiuil.lab.cleverdictionary.ui.navigation.components.TopBarConfig
import kotlinx.coroutines.launch

@Composable
fun NavApp(
    viewModel: NavViewModel = hiltViewModel()
) {
    val user by viewModel.user.collectAsState()

    val startDestination = if (user != null && user!!.isEmailVerified) {
        Screen.Home.route
    } else {
        Screen.Auth.route
    }

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val topBarConfigState = remember { mutableStateOf(TopBarConfig()) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    CompositionLocalProvider(
        LocalTopBarConfig provides topBarConfigState,
        LocalSnackbarManager provides SnackbarManager
    ) {
        Scaffold(
            modifier = Modifier.imePadding(),
            topBar = { AppTopBar() },
            bottomBar = { AppBottomBar(navController, currentRoute ?: "") },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { innerPadding ->
            NavGraph(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                startDestination = startDestination
            )
        }

        LaunchedEffect(snackbarHostState) {
            SnackbarManager.messages.collect { message ->
                coroutineScope.launch {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(message)
                }
            }
        }
    }
}