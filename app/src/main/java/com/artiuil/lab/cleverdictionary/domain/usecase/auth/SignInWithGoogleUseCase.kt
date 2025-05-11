package com.artiuil.lab.cleverdictionary.domain.usecase.auth

import com.artiuil.lab.cleverdictionary.domain.repository.auth.AuthError
import com.artiuil.lab.cleverdictionary.domain.repository.auth.AuthException
import com.artiuil.lab.cleverdictionary.domain.repository.auth.AuthRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(idToken: String): Result<FirebaseUser?> {
        if (idToken.isEmpty()) {
            return Result.failure(AuthException(AuthError.EmptyGoogleToken))
        }
        return repository.signInWithGoogle(idToken)
    }
}
