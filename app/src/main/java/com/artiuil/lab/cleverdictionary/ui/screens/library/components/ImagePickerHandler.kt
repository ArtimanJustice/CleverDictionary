package com.artiuil.lab.cleverdictionary.ui.screens.library.components

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.artiuil.lab.cleverdictionary.domain.dto.words.WordInputDto
import com.artiuil.lab.cleverdictionary.ui.screens.image.ImagePicker
import com.artiuil.lab.cleverdictionary.ui.screens.image.rememberImagePicker

@Composable
fun ImagePickerHandler(
    showDialog: Boolean,
    currentImageIndex: Int?,
    wordInputDtos: SnapshotStateList<WordInputDto>,
    uploadImageForWord: (Uri, Int) -> Unit,
    onDismiss: () -> Unit
) {
    val imagePicker = rememberImagePicker { uri ->
        uri?.let { selectedUri ->
            currentImageIndex?.let { index ->
                wordInputDtos[index] = wordInputDtos[index].copy(localImageUri = selectedUri)
                uploadImageForWord(selectedUri, index)
            }
        }
    }

    if (showDialog) {
        ImagePicker(
            onDismiss = onDismiss,
            onCameraPicked = {
                onDismiss()
                imagePicker.launchCamera()
            },
            onGalleryPicked = {
                onDismiss()
                imagePicker.launchGallery()
            }
        )
    }
}

