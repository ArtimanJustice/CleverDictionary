package com.artiuil.lab.cleverdictionary.ui.screens.library.editset

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artiuil.lab.cleverdictionary.domain.entity.words.WordsSetEntity
import com.artiuil.lab.cleverdictionary.domain.repository.words.EditSetError
import com.artiuil.lab.cleverdictionary.domain.usecase.images.UploadImageUseCase
import com.artiuil.lab.cleverdictionary.domain.usecase.words.EditSetUseCase
import com.artiuil.lab.cleverdictionary.domain.usecase.words.GetSetUseCase
import com.artiuil.lab.cleverdictionary.ui.screens.image.ImageUploadState
import com.artiuil.lab.cleverdictionary.utils.compressImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditSetViewModel @Inject constructor(
    private val getSetUseCase: GetSetUseCase,
    private val editSetUseCase: EditSetUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow<EditSetState>(EditSetState.Loading)
    val state: StateFlow<EditSetState> = _state.asStateFlow()

    private val _uploadState = MutableStateFlow<ImageUploadState>(ImageUploadState.Idle)
    val uploadState: StateFlow<ImageUploadState> = _uploadState.asStateFlow()

    init {
        val setId = savedStateHandle.get<String>("id")
        if (setId.isNullOrEmpty()) {
            _state.value = EditSetState.Error(EditSetError.InvalidSetId)
        } else {
            viewModelScope.launch {
                val set = getSetUseCase(setId)
                _state.value = if (set != null) {
                    EditSetState.Success(set)
                } else {
                    EditSetState.Error(EditSetError.SetNotFound)
                }
            }
        }
    }

    fun uploadImageForWord(uri: Uri, index: Int, context: Context) {
        viewModelScope.launch {
            val imageBytes = compressImage(context, uri, targetWidth = 1024, quality = 85)
            if (imageBytes != null) {
                val path = "images/${System.currentTimeMillis()}.jpg"
                val result = uploadImageUseCase(path, imageBytes)
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

    fun editSet(wordsSet: WordsSetEntity, onComplete: () -> Unit) {
        viewModelScope.launch {
            _state.value = EditSetState.Loading
            try {
                editSetUseCase(wordsSet)
                onComplete()
            } catch (_: Exception) {
                _state.value = EditSetState.Error(EditSetError.EditSetFailure)
            }
        }
    }

    fun resetImageUploadState() {
        _uploadState.value = ImageUploadState.Idle
    }
}
