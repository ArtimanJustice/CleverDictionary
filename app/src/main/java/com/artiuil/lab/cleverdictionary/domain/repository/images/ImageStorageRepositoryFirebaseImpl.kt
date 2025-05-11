package com.artiuil.lab.cleverdictionary.domain.repository.images

import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageStorageRepositoryFirebaseImpl @Inject constructor() : ImageStorageRepository {
    private val storageRef = FirebaseStorage.getInstance().reference

    override suspend fun uploadImage(path: String, bytes: ByteArray): Result<String> {
        return try {
            val imageRef = storageRef.child(path)
            imageRef.putBytes(bytes).await()
            val downloadUrl = imageRef.downloadUrl.await().toString()
            Result.success(downloadUrl)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun downloadImage(path: String): Result<ByteArray> {
        return try {
            val imageRef = storageRef.child(path)
            val maxSize: Long = 10 * 1024 * 1024
            val bytes = imageRef.getBytes(maxSize).await()
            Result.success(bytes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}