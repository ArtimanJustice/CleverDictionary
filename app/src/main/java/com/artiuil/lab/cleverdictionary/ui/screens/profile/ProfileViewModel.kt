package com.artiuil.lab.cleverdictionary.ui.screens.profile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artiuil.lab.cleverdictionary.domain.usecase.images.UploadImageUseCase
import com.artiuil.lab.cleverdictionary.domain.usecase.user.GetUserUseCase
import com.artiuil.lab.cleverdictionary.domain.usecase.user.UpdateUserUseCase
import com.artiuil.lab.cleverdictionary.utils.compressImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val uploadImageUseCase: UploadImageUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getUserUseCase().let { user ->
                if (user != null) _state.value = ProfileState.Success(user)
                else                _state.value = ProfileState.Error("Failed to load user")
            }
        }
    }

    fun onChangeName(newName: String) {
        val current = (_state.value as? ProfileState.Success)?.user ?: return
        viewModelScope.launch {
            updateUserUseCase(mapOf("userName" to newName))
            _state.value = ProfileState.Success(current.copy(userName = newName))
        }
    }

    fun onChangeAvatar(uri: Uri, context: Context) {
        val current = (_state.value as? ProfileState.Success)?.user ?: return
        viewModelScope.launch {
            // compressImage: suspend fun compressImage(ctx, uri, targetWidth, quality): ByteArray?
            val bytes = compressImage(context, uri, targetWidth = 1024, quality = 85)
            if (bytes != null) {
                val path = "avatars/${System.currentTimeMillis()}.jpg"
                uploadImageUseCase(path, bytes)
                    .onSuccess { url ->
                        updateUserUseCase(mapOf("avatarImageUrl" to url))
                        _state.value = ProfileState.Success(current.copy(avatarImageUrl = url))
                    }
                    .onFailure {
                        // handle error, e.g. show toast via side-effect
                    }
            }
        }
    }
}