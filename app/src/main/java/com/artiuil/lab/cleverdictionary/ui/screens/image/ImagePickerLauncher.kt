package com.artiuil.lab.cleverdictionary.ui.screens.image

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.artiuil.lab.cleverdictionary.R
import com.artiuil.lab.cleverdictionary.utils.createImageFileUri

interface ImagePickerLauncher {
    fun launchCamera()
    fun launchGallery()
}

@Composable
fun rememberImagePicker(onImagePicked: (Uri?) -> Unit): ImagePickerLauncher {
    val context = LocalContext.current
    var cameraImageUri by remember { mutableStateOf<Uri?>(null) }


    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && cameraImageUri != null) {
            onImagePicked(cameraImageUri)
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraImageUri = createImageFileUri(context)
            cameraImageUri?.let { uri ->
                cameraLauncher.launch(uri)
            }
        } else {
            Toast.makeText(context,
                context.getString(R.string.camera_permission_denied_message), Toast.LENGTH_SHORT).show()
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onImagePicked(uri)
    }

    return object : ImagePickerLauncher {
        override fun launchCamera() {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
                cameraImageUri = createImageFileUri(context)
                cameraImageUri?.let { uri ->
                    cameraLauncher.launch(uri)
                }
            } else {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }

        override fun launchGallery() {
            galleryLauncher.launch("image/*")
        }
    }
}
