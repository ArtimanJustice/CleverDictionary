package com.artiuil.lab.cleverdictionary.domain.usecase.auth

import com.artiuil.lab.cleverdictionary.domain.repository.auth.AuthError
import com.artiuil.lab.cleverdictionary.domain.repository.auth.AuthException
import com.artiuil.lab.cleverdictionary.domain.repository.auth.AuthRepository
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String): Result<Unit> {
        if (email.isEmpty()) {
            return Result.failure(AuthException(AuthError.EmptyEmail))
        }
        return repository.resetPassword(email)
    }
}