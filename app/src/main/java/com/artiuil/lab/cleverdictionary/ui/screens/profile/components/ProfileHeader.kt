package com.artiuil.lab.cleverdictionary.ui.screens.profile.components

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.artiuil.lab.cleverdictionary.R
import com.artiuil.lab.cleverdictionary.domain.entity.user.UserEntity

@Composable
fun ProfileHeader(
    user: UserEntity,
    localAvatarUri: Uri?,
    onAvatarClick: () -> Unit,
    onNameClick: (String) -> Unit
) {
    val model: Any = localAvatarUri ?: user.avatarImageUrl.orEmpty()

    AsyncImage(
        model = model,
        contentDescription = "Avatar of ${user.userName}",
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.ic_avatar_matcha),
        error = painterResource(R.drawable.ic_avatar_matcha),
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .clickable { onAvatarClick() }
    )

    Spacer(Modifier.height(12.dp))

    Text(
        text = user.userName,
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.clickable { onNameClick(user.userName) }
    )
}