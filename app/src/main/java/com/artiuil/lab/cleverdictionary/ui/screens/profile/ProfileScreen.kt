package com.artiuil.lab.cleverdictionary.ui.screens.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.artiuil.lab.cleverdictionary.domain.entity.user.UserEntity
import com.artiuil.lab.cleverdictionary.ui.screens.loading.LoadingIndicator
import com.artiuil.lab.cleverdictionary.ui.screens.profile.components.EditUsernameDialog
import com.artiuil.lab.cleverdictionary.ui.screens.profile.components.ProfileHeader
import com.artiuil.lab.cleverdictionary.ui.theme.AppTheme

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    var localAvatarUri by remember { mutableStateOf<Uri?>(null) }

    val picker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            localAvatarUri = it
            viewModel.onChangeAvatar(it, context)
        }
    }

    when (state) {
        ProfileState.Loading -> {
            LoadingIndicator()
        }

        is ProfileState.Error -> {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text((state as ProfileState.Error).message)
            }
        }

        is ProfileState.Success -> {
            val user = (state as ProfileState.Success).user
            ProfileScreenContent(
                user = user,
                localAvatarUri = localAvatarUri,
                onAvatarClick = { picker.launch("image/*") },
                onNameClick = { viewModel.onChangeName(it) }
            )
        }
    }
}


@Composable
fun ProfileScreenContent(
    user: UserEntity,
    localAvatarUri: Uri?,
    onAvatarClick: () -> Unit,
    onNameClick: (String) -> Unit
) {
    var editMode by remember { mutableStateOf(false) }
    var draftName by remember { mutableStateOf(user.userName) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ProfileHeader(
            user = user,
            localAvatarUri = localAvatarUri,
            onAvatarClick = onAvatarClick,
            onNameClick = {
                draftName = it
                editMode = true
            }
        )
    }

    if (editMode) {
        EditUsernameDialog(
            currentName = draftName,
            onNameChange = { draftName = it },
            onConfirm = {
                onNameClick(draftName)
                editMode = false
            },
            onDismiss = { editMode = false }
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    AppTheme {
        val dummyUser = UserEntity(
            id = "1",
            email = "artiuil@example.com",
            userName = "Artiuil",
            avatarImageUrl = null
        )

        var localUri: Uri? by remember { mutableStateOf(null) }

        ProfileScreenContent(
            user = dummyUser,
            localAvatarUri = localUri,
            onAvatarClick = {},
            onNameClick = {}
        )
    }
}