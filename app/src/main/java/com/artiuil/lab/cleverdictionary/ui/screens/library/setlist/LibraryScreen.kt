package com.artiuil.lab.cleverdictionary.ui.screens.library.setlist

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.artiuil.lab.cleverdictionary.R
import com.artiuil.lab.cleverdictionary.domain.entity.words.WordEntity
import com.artiuil.lab.cleverdictionary.domain.entity.words.WordsSetEntity
import com.artiuil.lab.cleverdictionary.domain.repository.words.LibraryError
import com.artiuil.lab.cleverdictionary.ui.navigation.components.LocalNavController
import com.artiuil.lab.cleverdictionary.ui.navigation.components.LocalTopBarConfig
import com.artiuil.lab.cleverdictionary.ui.navigation.components.Screen
import com.artiuil.lab.cleverdictionary.ui.navigation.components.TopBarConfig
import com.artiuil.lab.cleverdictionary.ui.screens.library.components.SquareSetCard
import com.artiuil.lab.cleverdictionary.ui.screens.loading.LoadingIndicator
import com.artiuil.lab.cleverdictionary.ui.theme.AppTheme

@Composable
fun LibraryScreen(viewModel: LibraryViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val navController = LocalNavController.current
    val state by viewModel.state.collectAsState()

    val topBarConfig = LocalTopBarConfig.current
    topBarConfig.value = TopBarConfig(
        title = stringResource(R.string.library_screen_name),
        actions = {
            IconButton(onClick = {
                navController.navigate(Screen.CreateSet.route)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_set_button_content_description)
                )
            }
        }
    )

    Crossfade(targetState = state) { currentState ->
        when (currentState) {
            is LibraryState.Loading -> {
                LoadingIndicator()
            }

            is LibraryState.Success -> {
                LibraryScreenContent(
                    sets = currentState.sets,
                    onSetSelected = { setId ->
                        navController.navigate(Screen.ShowSet.createRoute(setId))
                    },
                    onEdit = { setId ->
                        navController.navigate(Screen.EditSet.createRoute(setId))
                    },
                    onDelete = { setId ->
                        viewModel.deleteSet(setId)
                    }
                )
            }

            is LibraryState.Error -> {
                val errorMessage = when (currentState.error) {
                    LibraryError.UnknownError -> context.getString(R.string.library_unknown_error)
                    LibraryError.NetworkError -> context.getString(R.string.library_network_error)
                }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = errorMessage)
                }
            }
        }
    }
}

@Composable
fun LibraryScreenContent(
    sets: List<WordsSetEntity>,
    onSetSelected: (String) -> Unit,
    onEdit: (String) -> Unit,
    onDelete: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(
            items = sets,
            key = { set -> set.id }
        ) { set ->
            SquareSetCard(
                set = set,
                onSetSelected = onSetSelected,
                onEdit = onEdit,
                onDelete = onDelete
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LibraryScreenPreview() {
    val sets = (1..5).map { setId ->
        WordsSetEntity(
            id = setId.toString(),
            title = "simple",
            imageUrl = null,
            words = (1..10).map { wordId ->
                WordEntity(
                    id = wordId.toString(),
                    term = "Word $wordId",
                    definition = "Definition $wordId",
                    imageUrl = null
                )
            }
        )
    }

    AppTheme {
        LibraryScreenContent(sets, onSetSelected = {}, onEdit = {}, onDelete = {})
    }
}
