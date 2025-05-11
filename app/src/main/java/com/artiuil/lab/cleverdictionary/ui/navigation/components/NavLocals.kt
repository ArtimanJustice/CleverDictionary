package com.artiuil.lab.cleverdictionary.ui.navigation.components

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController

val LocalNavController =
    staticCompositionLocalOf<NavController> {
        error("Can't access NavController")
    }

val LocalTopBarConfig = compositionLocalOf<MutableState<TopBarConfig>> {
    error("No TopBarConfig provided")
}

val LocalSnackbarManager = staticCompositionLocalOf<SnackbarManager> {
    error("SnackbarManager not provided")
}
