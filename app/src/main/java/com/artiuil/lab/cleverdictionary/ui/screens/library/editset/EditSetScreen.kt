package com.artiuil.lab.cleverdictionary.ui.screens.library.editset

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.artiuil.lab.cleverdictionary.R
import com.artiuil.lab.cleverdictionary.domain.dto.words.WordInputDto
import com.artiuil.lab.cleverdictionary.domain.entity.words.edit
import com.artiuil.lab.cleverdictionary.domain.repository.words.EditSetError
import com.artiuil.lab.cleverdictionary.ui.navigation.components.LocalNavController
import com.artiuil.lab.cleverdictionary.ui.navigation.components.LocalSnackbarManager
import com.artiuil.lab.cleverdictionary.ui.screens.library.components.ConfigureTopBar
import com.artiuil.lab.cleverdictionary.ui.screens.library.components.CreateOrEditSetScreenContent
import com.artiuil.lab.cleverdictionary.ui.screens.library.components.HandleImageUploadState
import com.artiuil.lab.cleverdictionary.ui.screens.library.components.ImagePickerHandler
import com.artiuil.lab.cleverdictionary.ui.screens.loading.LoadingIndicator
import com.artiuil.lab.cleverdictionary.ui.theme.AppTheme

@Composable
fun EditSetScreen(
    viewModel: EditSetViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val navController = LocalNavController.current
    val snackbarManager = LocalSnackbarManager.current

    val editState by viewModel.state.collectAsState()
    val uploadState by viewModel.uploadState.collectAsState()

    var showImagePickerDialog by remember { mutableStateOf(false) }
    var currentImageIndex by remember { mutableStateOf<Int?>(null) }
    var title by remember { mutableStateOf("") }
    val wordInputDtos = remember { mutableStateListOf<WordInputDto>() }

    LaunchedEffect(editState) {
        if (editState is EditSetState.Success) {
            val wordsSet = (editState as EditSetState.Success).wordsSet
            title = wordsSet.title
            wordInputDtos.clear()
            wordInputDtos.addAll(wordsSet.words.map {
                WordInputDto(
                    term = it.term,
                    definition = it.definition,
                    localImageUri = null,
                    remoteImageUrl = it.imageUrl
                )
            })
        }
    }

    ConfigureTopBar(
        title = context.getString(R.string.edit_set_screen_name),
        onClose = { navController.popBackStack() },
        onSave = {
            val wordsSet = (editState as EditSetState.Success).wordsSet
            viewModel.editSet(
                wordsSet.edit(title, wordInputDtos)
            ) {
                navController.popBackStack()
            }
        },
        actionsEnabled = editState is EditSetState.Success
    )

    when (editState) {
        is EditSetState.Loading -> {
            LoadingIndicator()
        }

        is EditSetState.Error -> {
            LaunchedEffect(editState) {
                val errorMessage = when ((editState as EditSetState.Error).error) {
                    EditSetError.InvalidSetId -> context.getString(R.string.edit_set_error_invalid_set_id)
                    EditSetError.SetNotFound -> context.getString(R.string.edit_set_error_set_not_found)
                    EditSetError.EditSetFailure -> context.getString(R.string.edit_set_error_failure)
                }
                snackbarManager.showMessage(errorMessage)
                navController.popBackStack()
            }
        }

        is EditSetState.Success -> {
            CreateOrEditSetScreenContent(
                title = title,
                onTitleChange = { title = it },
                wordInputDtos = wordInputDtos,
                onWordInputChange = { index, newInput ->
                    wordInputDtos[index] = newInput
                },
                onAddWordInput = {
                    wordInputDtos.add(WordInputDto("", "", null, null))
                },
                onDeleteWordInput = { index ->
                    wordInputDtos.removeAt(index)
                },
                onCameraClick = { index ->
                    currentImageIndex = index
                    showImagePickerDialog = true
                }
            )

            HandleImageUploadState(
                uploadState = uploadState,
                wordInputDtos = wordInputDtos,
                resetImageUploadState = { viewModel.resetImageUploadState() },
                snackbarManager = snackbarManager,
                context = context
            )

            ImagePickerHandler(
                showDialog = showImagePickerDialog,
                currentImageIndex = currentImageIndex,
                wordInputDtos = wordInputDtos,
                uploadImageForWord = { uri, index ->
                    viewModel.uploadImageForWord(uri, index, context)
                },
                onDismiss = { showImagePickerDialog = false }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditSetPreview() {
    AppTheme {
        CreateOrEditSetScreenContent(
            title = "Sample Set",
            onTitleChange = {},
            wordInputDtos = listOf(
                WordInputDto(term = "Hello", definition = "Greeting"),
                WordInputDto(term = "World", definition = "The Universe")
            ),
            onWordInputChange = { _, _ -> },
            onAddWordInput = {},
            onDeleteWordInput = {},
            onCameraClick = {}
        )
    }
}