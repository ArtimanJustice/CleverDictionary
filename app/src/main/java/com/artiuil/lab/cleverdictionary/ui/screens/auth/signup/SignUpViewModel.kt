package com.artiuil.lab.cleverdictionary.ui.screens.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artiuil.lab.cleverdictionary.domain.repository.auth.AuthError
import com.artiuil.lab.cleverdictionary.domain.repository.auth.AuthException
import com.artiuil.lab.cleverdictionary.domain.usecase.auth.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    private val _uiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Idle)
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    fun onConfirmPasswordChanged(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
    }

    fun resetUiState() {
        _uiState.value = SignUpUiState.Idle
    }

    fun signUp() {
        if (_password.value.trim() != _confirmPassword.value.trim()) {
            _uiState.value = SignUpUiState.Error(AuthError.PasswordsDoNotMatch)
            return
        }
        viewModelScope.launch {
            _uiState.value = SignUpUiState.Loading
            val result = signUpUseCase(_email.value.trim(), _password.value.trim())
            result.fold(
                onSuccess = { user ->
                    _uiState.value = SignUpUiState.Success("Registration success")
                },
                onFailure = { error ->
                    val authError =
                        if (error is AuthException) error.error else AuthError.IncorrectEmail
                    _uiState.value = SignUpUiState.Error(
                        when (authError) {
                            AuthError.EmptyCredentials -> AuthError.EmptyCredentials
                            else -> AuthError.EmailAlreadyExist
                        }
                    )
                }
            )
        }
    }
}
