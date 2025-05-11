package com.artiuil.lab.cleverdictionary.ui.screens.library.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.artiuil.lab.cleverdictionary.R
import com.artiuil.lab.cleverdictionary.domain.dto.words.WordInputDto
import com.artiuil.lab.cleverdictionary.ui.navigation.components.SnackbarManager
import com.artiuil.lab.cleverdictionary.ui.screens.image.ImageUploadState

@Composable
fun HandleImageUploadState(
    uploadState: ImageUploadState,
    wordInputDtos: SnapshotStateList<WordInputDto>,
    resetImageUploadState: () -> Unit,
    snackbarManager: SnackbarManager,
    context: Context
) {
    LaunchedEffect(uploadState) {
        when (uploadState) {
            is ImageUploadState.Success -> {
                wordInputDtos[uploadState.index] = wordInputDtos[uploadState.index].copy(
                    remoteImageUrl = uploadState.downloadUrl
                )
                resetImageUploadState()
            }
            is ImageUploadState.Error -> {
                snackbarManager.showMessage(context.getString(R.string.create_set_error_upload))
                resetImageUploadState()
            }
            is ImageUploadState.CompressionError -> {
                snackbarManager.showMessage(context.getString(R.string.create_set_error_compress))
                resetImageUploadState()
            }
            else -> Unit
        }
    }
}