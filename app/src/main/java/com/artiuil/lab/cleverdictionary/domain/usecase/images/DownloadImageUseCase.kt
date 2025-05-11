package com.artiuil.lab.cleverdictionary.domain.usecase.images

import com.artiuil.lab.cleverdictionary.domain.repository.images.ImageStorageRepository

class DownloadImageUseCase(private val repository: ImageStorageRepository) {
    suspend operator fun invoke(path: String): Result<ByteArray> {
        return repository.downloadImage(path)
    }
}