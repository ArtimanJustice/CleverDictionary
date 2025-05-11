package com.artiuil.lab.cleverdictionary.ui.navigation.components

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Library : Screen("library")
    data object Profile : Screen("profile")
    data object CreateSet : Screen("create_set")
    data object EditSet : Screen("edit_set/{id}") {
        fun createRoute(id: String) = "edit_set/$id"
    }
    data object ShowSet : Screen("show_set/{id}") {
        fun createRoute(id: String) = "show_set/$id"
    }
    data object Auth : Screen("auth")
}
