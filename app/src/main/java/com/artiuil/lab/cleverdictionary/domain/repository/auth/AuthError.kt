package com.artiuil.lab.cleverdictionary.domain.repository.auth

sealed class AuthError {
    object EmptyCredentials : AuthError()
    object EmptyEmail : AuthError()
    object IncorrectEmail : AuthError()
    object IncorrectCredentials : AuthError()
    object UnknownSignInError : AuthError()
    object EmailNotVerified : AuthError()
    object PasswordsDoNotMatch : AuthError()
    object EmailAlreadyExist : AuthError()
    object EmptyGoogleToken : AuthError()
}

class AuthException(val error: AuthError) : Exception() {
    override val message: String?
        get() = error.toString()
}