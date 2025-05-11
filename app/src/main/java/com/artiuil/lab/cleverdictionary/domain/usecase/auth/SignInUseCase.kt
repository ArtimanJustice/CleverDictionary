package com.artiuil.lab.cleverdictionary.domain.usecase.auth

import com.artiuil.lab.cleverdictionary.domain.dto.auth.AuthDataDto
import com.artiuil.lab.cleverdictionary.domain.repository.auth.AuthError
import com.artiuil.lab.cleverdictionary.domain.repository.auth.AuthException
import com.artiuil.lab.cleverdictionary.domain.repository.auth.AuthRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<FirebaseUser?> {
        if (email.isEmpty() || password.isEmpty()) {
            return Result.failure(AuthException(AuthError.EmptyCredentials))
        }
        return repository.signIn(AuthDataDto(email, password))
    }
}