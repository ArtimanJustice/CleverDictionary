package com.artiuil.lab.cleverdictionary.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.artiuil.lab.cleverdictionary.ui.navigation.AppNavHost
import com.artiuil.lab.cleverdictionary.ui.screens.main.components.MainBottomBar
import com.artiuil.lab.cleverdictionary.ui.screens.main.components.MainTopBar
import com.artiuil.lab.cleverdictionary.ui.theme.AppTheme

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = { MainTopBar() },
        bottomBar = { MainBottomBar(navController) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen()
    }
}