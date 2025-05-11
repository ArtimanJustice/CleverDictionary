package com.artiuil.lab.cleverdictionary.domain.repository.images

interface ImageStorageRepository {
    suspend fun uploadImage(path: String, bytes: ByteArray): Result<String>
    suspend fun downloadImage(path: String): Result<ByteArray>
}