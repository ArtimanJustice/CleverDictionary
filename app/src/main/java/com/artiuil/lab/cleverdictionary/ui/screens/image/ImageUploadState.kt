package com.artiuil.lab.cleverdictionary.ui.screens.image

sealed class ImageUploadState {
    object Idle : ImageUploadState()
    data class Success(val index: Int, val downloadUrl: String) : ImageUploadState()
    object Error : ImageUploadState()
    object CompressionError : ImageUploadState()
}
