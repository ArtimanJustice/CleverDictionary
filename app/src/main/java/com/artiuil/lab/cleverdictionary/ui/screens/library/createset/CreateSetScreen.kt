package com.artiuil.lab.cleverdictionary.ui.screens.library.createset

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
import com.artiuil.lab.cleverdictionary.domain.entity.words.WordsSetEntity
import com.artiuil.lab.cleverdictionary.domain.entity.words.new
import com.artiuil.lab.cleverdictionary.ui.navigation.components.LocalNavController
import com.artiuil.lab.cleverdictionary.ui.navigation.components.LocalSnackbarManager
import com.artiuil.lab.cleverdictionary.ui.screens.library.components.ConfigureTopBar
import com.artiuil.lab.cleverdictionary.ui.screens.library.components.CreateOrEditSetScreenContent
import com.artiuil.lab.cleverdictionary.ui.screens.library.components.HandleImageUploadState
import com.artiuil.lab.cleverdictionary.ui.screens.library.components.ImagePickerHandler
import com.artiuil.lab.cleverdictionary.ui.screens.loading.LoadingIndicator
import com.artiuil.lab.cleverdictionary.ui.theme.AppTheme

@Composable
fun CreateSetScreen(
    viewModel: CreateSetViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val navController = LocalNavController.current
    val snackbarManager = LocalSnackbarManager.current

    val createState by viewModel.state.collectAsState()
    val uploadState by viewModel.uploadState.collectAsState()

    var showImagePickerDialog by remember { mutableStateOf(false) }
    var currentImageIndex by remember { mutableStateOf<Int?>(null) }
    var title by remember { mutableStateOf("") }
    val wordInputDtos = remember { mutableStateListOf(WordInputDto("", "")) }

    ImagePickerHandler(
        showDialog = showImagePickerDialog,
        currentImageIndex = currentImageIndex,
        wordInputDtos = wordInputDtos,
        uploadImageForWord = { uri, index ->
            wordInputDtos[index] = wordInputDtos[index].copy(localImageUri = uri)
            viewModel.uploadImageForWord(uri, index, context)
        },
        onDismiss = { showImagePickerDialog = false }
    )

    HandleImageUploadState(
        uploadState = uploadState,
        wordInputDtos = wordInputDtos,
        resetImageUploadState = { viewModel.resetMainState() },
        snackbarManager = snackbarManager,
        context = context
    )

    ConfigureTopBar(
        title = context.getString(R.string.create_set_screen_name),
        onClose = { navController.popBackStack() },
        onSave = {
            viewModel.createSet(
                WordsSetEntity.new(title, wordInputDtos)
            ) {
                navController.popBackStack()
            }
        },
        actionsEnabled = createState is CreateSetState.Idle
    )

    when (createState) {
        is CreateSetState.Saving -> LoadingIndicator()

        is CreateSetState.Error -> LaunchedEffect(createState) {
            snackbarManager.showMessage(
                context.getString(R.string.create_set_error_create_set)
            )
            viewModel.resetMainState()
        }

        is CreateSetState.Idle -> CreateOrEditSetScreenContent(
            title = title,
            onTitleChange = { title = it },
            wordInputDtos = wordInputDtos,
            onWordInputChange = { idx, dto -> wordInputDtos[idx] = dto },
            onAddWordInput = { wordInputDtos.add(WordInputDto("", "")) },
            onDeleteWordInput = { idx -> wordInputDtos.removeAt(idx) },
            onCameraClick = { idx ->
                currentImageIndex = idx
                showImagePickerDialog = true
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun CreateSetPreview() {
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