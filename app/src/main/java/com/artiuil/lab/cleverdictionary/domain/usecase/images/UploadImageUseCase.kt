package com.artiuil.lab.cleverdictionary.domain.usecase.images

import com.artiuil.lab.cleverdictionary.domain.repository.images.ImageStorageRepository

class UploadImageUseCase(private val repository: ImageStorageRepository) {
    suspend operator fun invoke(path: String, bytes: ByteArray): Result<String> {
        return repository.uploadImage(path, bytes)
    }
}
