package com.artiuil.lab.cleverdictionary.ui.screens.library.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artiuil.lab.cleverdictionary.R
import com.artiuil.lab.cleverdictionary.domain.dto.words.WordInputDto
import com.artiuil.lab.cleverdictionary.ui.theme.AppTheme

@Composable
fun CreateOrEditSetScreenContent(
    title: String,
    onTitleChange: (String) -> Unit,
    wordInputDtos: List<WordInputDto>,
    onWordInputChange: (Int, WordInputDto) -> Unit,
    onAddWordInput: () -> Unit,
    onDeleteWordInput: (Int) -> Unit,
    onCameraClick: (Int) -> Unit
) {
    val scrollState = rememberScrollState()
    LaunchedEffect(key1 = wordInputDtos.size) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            TextField(
                value = title,
                onValueChange = onTitleChange,
                label = { Text(stringResource(R.string.set_title)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            wordInputDtos.forEachIndexed { index, dto ->
                WordInputBlock(
                    term = dto.term,
                    definition = dto.definition,
                    localImageUri = dto.localImageUri,
                    remoteImageUrl = dto.remoteImageUrl,
                    onTermChange = { newTerm ->
                        onWordInputChange(index, dto.copy(term = newTerm))
                    },
                    onDefinitionChange = { newDefinition ->
                        onWordInputChange(index, dto.copy(definition = newDefinition))
                    },
                    onCameraClick = { onCameraClick(index) },
                    onDelete = { onDeleteWordInput(index) }
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        FloatingActionButton(
            onClick = onAddWordInput,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = stringResource(R.string.add_new_word_button_description)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CreateOrEditSetPreview() {
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