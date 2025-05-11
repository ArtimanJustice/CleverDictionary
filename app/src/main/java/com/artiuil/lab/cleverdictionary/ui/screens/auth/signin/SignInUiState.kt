package com.artiuil.lab.cleverdictionary.ui.screens.auth.signin

import com.artiuil.lab.cleverdictionary.domain.repository.auth.AuthError
import com.google.firebase.auth.FirebaseUser

sealed class SignInUiState {
    object Idle : SignInUiState()
    object Loading : SignInUiState()
    data class Success(val messageResId: Int) : SignInUiState()
    data class Error(val error: AuthError) : SignInUiState()
    data class SignedIn(val user: FirebaseUser) : SignInUiState()
}