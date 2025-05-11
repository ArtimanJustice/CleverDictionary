package com.artiuil.lab.cleverdictionary.ui.screens.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artiuil.lab.cleverdictionary.R
import com.artiuil.lab.cleverdictionary.domain.repository.auth.AuthError
import com.artiuil.lab.cleverdictionary.domain.repository.auth.AuthException
import com.artiuil.lab.cleverdictionary.domain.usecase.auth.ResetPasswordUseCase
import com.artiuil.lab.cleverdictionary.domain.usecase.auth.SignInUseCase
import com.artiuil.lab.cleverdictionary.domain.usecase.auth.SignInWithGoogleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _uiState = MutableStateFlow<SignInUiState>(SignInUiState.Idle)
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    fun forgotPassword() {
        viewModelScope.launch {
            _uiState.value = SignInUiState.Loading
            val result = resetPasswordUseCase(_email.value.trim())
            result.fold(
                onSuccess = {
                    _uiState.value = SignInUiState.Success(R.string.reset_link_sent_message)
                },
                onFailure = { error ->
                    val authError =
                        if (error is AuthException) error.error else AuthError.IncorrectEmail
                    _uiState.value = when (authError) {
                        AuthError.EmptyEmail -> SignInUiState.Error(AuthError.EmptyEmail)
                        else -> SignInUiState.Error(AuthError.IncorrectEmail)
                    }
                }
            )
        }
    }

    fun signIn() {
        viewModelScope.launch {
            _uiState.value = SignInUiState.Loading
            val result = signInUseCase(_email.value.trim(), _password.value.trim())
            result.fold(
                onSuccess = { user ->
                    if (user == null) {
                        _uiState.value = SignInUiState.Error(AuthError.UnknownSignInError)
                    } else {
                        _uiState.value = SignInUiState.SignedIn(user)
                    }
                },
                onFailure = { error ->
                    val authError =
                        if (error is AuthException) error.error else AuthError.IncorrectCredentials
                    _uiState.value = when (authError) {
                        AuthError.EmptyCredentials -> SignInUiState.Error(AuthError.EmptyCredentials)
                        AuthError.EmptyEmail -> SignInUiState.Error(AuthError.EmptyEmail)
                        else -> SignInUiState.Error(AuthError.IncorrectCredentials)
                    }
                }
            )
        }
    }

    fun signInWithGoogle(googleIdToken: String) {
        viewModelScope.launch {
            _uiState.value = SignInUiState.Loading
            val result = signInWithGoogleUseCase(googleIdToken)
            result.fold(
                onSuccess = { user ->
                    if (user == null) {
                        _uiState.value = SignInUiState.Error(AuthError.UnknownSignInError)
                    } else {
                        _uiState.value = SignInUiState.SignedIn(user)
                    }
                },
                onFailure = { error ->
                    val authError =
                        if (error is AuthException) error.error else AuthError.IncorrectCredentials
                    _uiState.value = when (authError) {
                        AuthError.EmptyGoogleToken -> SignInUiState.Error(AuthError.EmptyGoogleToken)
                        else -> SignInUiState.Error(AuthError.IncorrectCredentials)
                    }
                }
            )
        }
    }
}
