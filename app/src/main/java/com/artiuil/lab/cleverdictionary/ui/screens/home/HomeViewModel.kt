package com.artiuil.lab.cleverdictionary.ui.screens.home

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artiuil.lab.cleverdictionary.domain.usecase.images.UploadImageUseCase
import com.artiuil.lab.cleverdictionary.utils.compressImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val uploadImageUseCase: UploadImageUseCase
) : ViewModel() {
    private val _imageUri = mutableStateOf<Uri?>(null)
    val imageUri: State<Uri?> = _imageUri

    private val _uploadResult = mutableStateOf<String?>(null)
    val uploadResult: State<String?> = _uploadResult

    fun setImageUri(uri: Uri, context: Context) {
        _imageUri.value = uri
        viewModelScope.launch {
            val imageBytes = compressImage(context, uri, targetWidth = 1024, quality = 85)
            if (imageBytes != null) {
                val path = "images/${System.currentTimeMillis()}.jpg"
                val result = uploadImageUseCase.invoke(path, imageBytes)
                result.fold(
                    onSuccess = { downloadUrl ->
                        _uploadResult.value = downloadUrl
                    },
                    onFailure = { throwable ->
                        _uploadResult.value = "Ошибка загрузки: ${throwable.localizedMessage}"
                    }
                )
            } else {
                _uploadResult.value = "Не удалось получить байты изображения"
            }
        }
    }
}