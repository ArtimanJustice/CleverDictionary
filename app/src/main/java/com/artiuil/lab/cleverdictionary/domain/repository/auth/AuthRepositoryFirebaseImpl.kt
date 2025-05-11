package com.artiuil.lab.cleverdictionary.domain.repository.auth

import com.artiuil.lab.cleverdictionary.domain.dto.auth.AuthDataDto
import com.artiuil.lab.cleverdictionary.domain.entity.user.UserEntity
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryFirebaseImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    companion object {
        const val USERS_COLLECTION = "users"
    }

    private fun createUserDocumentIfNeeded(user: FirebaseUser) {
        val docRef = firestore.collection(USERS_COLLECTION).document(user.uid)
        docRef.get()
            .addOnSuccessListener { snap ->
                if (!snap.exists()) {
                    docRef.set(
                        UserEntity(
                            id = user.uid,
                            email = user.email.orEmpty(),
                            userName = user.email?.substringBefore("@").orEmpty(),
                            avatarImageUrl = null
                        )
                    )
                }
            }
    }

    override suspend fun signIn(authDataDto: AuthDataDto): Result<FirebaseUser?> =
        withContext(Dispatchers.IO) {
            try {
                val authResult = Tasks.await(
                    firebaseAuth.signInWithEmailAndPassword(
                        authDataDto.email,
                        authDataDto.password
                    )
                )
                val user = authResult.user
                if (user?.isEmailVerified == true) {
                    createUserDocumentIfNeeded(user)
                    Result.success(user)
                } else {
                    firebaseAuth.signOut()
                    Result.failure(AuthException(AuthError.EmailNotVerified))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun signUp(authDataDto: AuthDataDto): Result<FirebaseUser?> =
        withContext(Dispatchers.IO) {
            try {
                val authResult = Tasks.await(
                    firebaseAuth.createUserWithEmailAndPassword(
                        authDataDto.email,
                        authDataDto.password
                    )
                )
                val user = authResult.user
                user?.sendEmailVerification()
                Result.success(user)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser?> =
        withContext(Dispatchers.IO) {
            try {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                val authResult = Tasks.await(firebaseAuth.signInWithCredential(credential))
                val user = authResult.user
                if (user != null) {
                    createUserDocumentIfNeeded(user)
                }
                Result.success(user)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }


    override suspend fun resetPassword(email: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                Tasks.await(firebaseAuth.sendPasswordResetEmail(email))
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}
