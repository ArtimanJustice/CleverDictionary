package com.artiuil.lab.cleverdictionary.ui.screens.library.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.artiuil.lab.cleverdictionary.R
import com.artiuil.lab.cleverdictionary.domain.entity.words.WordEntity
import com.artiuil.lab.cleverdictionary.domain.entity.words.WordsSetEntity
import com.artiuil.lab.cleverdictionary.ui.theme.AppTheme
import com.artiuil.lab.cleverdictionary.utils.formatCreationDate
import com.artiuil.lab.cleverdictionary.utils.formatLastOpened

@Composable
fun SquareSetCard(
    set: WordsSetEntity,
    onSetSelected: (id: String) -> Unit,
    onEdit: (id: String) -> Unit,
    onDelete: (id: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(8.dp)) {
        Text(
            text = formatLastOpened(set.lastOpenedAt),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onSetSelected(set.id) }
                ) {
                    Text(
                        text = "${set.title} (${formatCreationDate(set.createdAt)})",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${set.words.size} " + stringResource(id = R.string.terms),
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                if (!set.imageUrl.isNullOrEmpty()) {
                    AsyncImage(
                        model = set.imageUrl,
                        contentDescription = stringResource(id = R.string.set_image_content_description),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                            .padding(horizontal = 8.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(onClick = { onEdit(set.id) }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(id = R.string.edit_set_content_description)
                        )
                    }
                    IconButton(onClick = { onDelete(set.id) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(id = R.string.delete_set_content_description)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SquareSetCardPreview() {
    val set = WordsSetEntity(
        id = "preview_id",
        title = "Test Set",
        imageUrl = "https://via.placeholder.com/150",
        words = listOf(
            WordEntity(
                id = "preview_word",
                term = "Sample Word",
                definition = "Sample Definition",
                imageUrl = ""
            )
        ),
        createdAt = System.currentTimeMillis() - 86400000L * 40,
        lastOpenedAt = System.currentTimeMillis() - 86400000L * 3
    )
    AppTheme {
        SquareSetCard(set = set, onSetSelected = {}, onEdit = {}, onDelete = {})
    }
}
