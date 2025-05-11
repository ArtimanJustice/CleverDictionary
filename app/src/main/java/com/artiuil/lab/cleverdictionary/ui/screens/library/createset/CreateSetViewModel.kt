package com.artiuil.lab.cleverdictionary.ui.screens.library.createset

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artiuil.lab.cleverdictionary.domain.entity.words.WordsSetEntity
import com.artiuil.lab.cleverdictionary.domain.usecase.images.UploadImageUseCase
import com.artiuil.lab.cleverdictionary.domain.usecase.words.CreateSetUseCase
import com.artiuil.lab.cleverdictionary.ui.screens.image.ImageUploadState
import com.artiuil.lab.cleverdictionary.utils.compressImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateSetViewModel @Inject constructor(
    private val createSetUseCase: CreateSetUseCase,
    private val uploadImageUseCase: UploadImageUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CreateSetState>(CreateSetState.Idle)
    val state: StateFlow<CreateSetState> = _state.asStateFlow()

    private val _uploadState = MutableStateFlow<ImageUploadState>(ImageUploadState.Idle)
    val uploadState: StateFlow<ImageUploadState> = _uploadState.asStateFlow()

    fun resetMainState() {
        _state.value = CreateSetState.Idle
    }

    fun resetImageUploadState() {
        _uploadState.value = ImageUploadState.Idle
    }

    fun uploadImageForWord(uri: Uri, index: Int, context: Context) {
        viewModelScope.launch {
            val imageBytes = compressImage(context, uri, targetWidth = 1024, quality = 85)
            if (imageBytes != null) {
                val path = "images/${System.currentTimeMillis()}.jpg"
                val result = uploadImageUseCase.invoke(path, imageBytes)
                result.fold(
                    { downloadUrl ->
                        _uploadState.value = ImageUploadState.Success(index, downloadUrl)
                    },
                    {
                        _uploadState.value = ImageUploadState.Error
                    }
                )
            } else {
                _uploadState.value = ImageUploadState.CompressionError
            }
        }
    }

    fun createSet(wordsSet: WordsSetEntity, onComplete: () -> Unit) {
        viewModelScope.launch {
            _state.value = CreateSetState.Saving
            try {
                createSetUseCase.invoke(wordsSet)
                onComplete()
            } catch (_: Exception) {
                _state.value = CreateSetState.Error
            }
        }
    }
}