package com.artiuil.lab.cleverdictionary.ui.screens.library.components

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.artiuil.lab.cleverdictionary.R
import com.artiuil.lab.cleverdictionary.ui.screens.loading.LoadingIndicator
import com.artiuil.lab.cleverdictionary.ui.theme.AppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WordInputBlock(
    term: String,
    definition: String,
    localImageUri: Uri? = null,
    remoteImageUrl: String? = null,
    onTermChange: (String) -> Unit,
    onDefinitionChange: (String) -> Unit,
    onCameraClick: () -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteOverlay by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(8.dp)
            .combinedClickable(
                onClick = {},
                onLongClick = { showDeleteOverlay = true },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                TextField(
                    value = term,
                    onValueChange = onTermChange,
                    label = { Text(stringResource(R.string.word_term)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 56.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = definition,
                    onValueChange = onDefinitionChange,
                    label = { Text(stringResource(R.string.word_definition)) },
                    trailingIcon = {
                        IconButton(onClick = onCameraClick) {
                            Icon(
                                imageVector = Icons.Rounded.Face,
                                contentDescription = stringResource(R.string.photo_icon_description)
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 56.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (localImageUri != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = localImageUri),
                            contentDescription = null,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                        if (remoteImageUrl == null) {
                            Spacer(modifier = Modifier.width(8.dp))
                            LoadingIndicator()
                        }
                    }
                } else if (remoteImageUrl != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = remoteImageUrl),
                            contentDescription = null,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = showDeleteOverlay,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut(),
            modifier = Modifier.matchParentSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable { showDeleteOverlay = false }
            ) {
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.delete_card_icon_description),
                        tint = Color.White
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CreateSetPreview() {
    AppTheme {
        WordInputBlock(
            term = "Sample",
            definition = "Definition",
            remoteImageUrl = null,
            onTermChange = {},
            onDefinitionChange = {},
            onCameraClick = {},
            onDelete = {}
        )
    }
}
