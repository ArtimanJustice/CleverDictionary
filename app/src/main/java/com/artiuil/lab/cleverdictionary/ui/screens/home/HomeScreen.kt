package com.artiuil.lab.cleverdictionary.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.artiuil.lab.cleverdictionary.ui.screens.image.ImagePicker
import com.artiuil.lab.cleverdictionary.ui.screens.image.rememberImagePicker
import com.artiuil.lab.cleverdictionary.ui.theme.AppTheme

@Composable
fun HomeScreen(videModel: HomeViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val imageUri = videModel.imageUri.value
    var showImagePickerDialog by remember { mutableStateOf(false) }

    val imagePicker = rememberImagePicker { uri ->
        uri?.let { videModel.setImageUri(it, context) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { showImagePickerDialog = true }) {
            Text(text = "Create Image")
        }

        Spacer(modifier = Modifier.height(16.dp))

        imageUri?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(model = uri),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }

    if (showImagePickerDialog) {
        ImagePicker(
            onDismiss = { showImagePickerDialog = false },
            onCameraPicked = {
                showImagePickerDialog = false
                imagePicker.launchCamera()
            },
            onGalleryPicked = {
                showImagePickerDialog = false
                imagePicker.launchGallery()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen()
    }
}