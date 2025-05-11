package com.artiuil.lab.cleverdictionary.domain.repository.auth

import com.artiuil.lab.cleverdictionary.domain.dto.auth.AuthDataDto
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun signIn(authDataDto: AuthDataDto): Result<FirebaseUser?>
    suspend fun signUp(authDataDto: AuthDataDto): Result<FirebaseUser?>
    suspend fun resetPassword(email: String): Result<Unit>
    suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser?>
}