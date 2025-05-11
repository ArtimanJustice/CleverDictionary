package com.artiuil.lab.cleverdictionary.ui.screens.auth.signup

import com.artiuil.lab.cleverdictionary.domain.repository.auth.AuthError

sealed class SignUpUiState {
    object Idle : SignUpUiState()
    object Loading : SignUpUiState()
    data class Success(val message: String) : SignUpUiState()
    data class Error(val error: AuthError) : SignUpUiState()
}
