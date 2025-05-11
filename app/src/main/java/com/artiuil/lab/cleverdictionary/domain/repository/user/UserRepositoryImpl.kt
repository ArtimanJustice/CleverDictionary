package com.artiuil.lab.cleverdictionary.domain.repository.user

import com.artiuil.lab.cleverdictionary.domain.entity.user.UserEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
) : UserRepository {

    companion object {
        const val USERS_COLLECTION = "users"
    }

    private fun getUserDocument(): DocumentReference {
        val uid = firebaseAuth.currentUser?.uid
            ?: throw Exception("User not authenticated")
        return firestore
            .collection(USERS_COLLECTION)
            .document(uid)
    }

    override suspend fun saveUser(user: UserEntity) {
        getUserDocument()
            .set(user, SetOptions.merge())
            .await()
    }

    override suspend fun getUser(): UserEntity? {
        val snapshot = getUserDocument()
            .get()
            .await()
        return snapshot.toObject(UserEntity::class.java)
    }

    override suspend fun updateUser(fields: Map<String, Any?>) {
        getUserDocument()
            .update(fields)
            .await()
    }
}
