package com.artiuil.lab.cleverdictionary.ui.screens.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.artiuil.lab.cleverdictionary.ui.screens.auth.signin.SignInScreen
import com.artiuil.lab.cleverdictionary.ui.screens.auth.signup.SignUpScreen

@Composable
fun AuthScreen() {
    var isSignUpMode by remember { mutableStateOf(false) }
    if (isSignUpMode) {
        SignUpScreen(onSwitchMode = { isSignUpMode = false })
    } else {
        SignInScreen(onSwitchMode = { isSignUpMode = true })
    }
}
