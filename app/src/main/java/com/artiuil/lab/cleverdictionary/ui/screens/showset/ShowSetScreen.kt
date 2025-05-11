package com.artiuil.lab.cleverdictionary.ui.screens.showset

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.artiuil.lab.cleverdictionary.domain.entity.words.WordsSetEntity
import com.artiuil.lab.cleverdictionary.ui.navigation.components.LocalNavController

@Composable
fun ShowSetScreen(viewModel: ShowSetViewModel = hiltViewModel()) {
    val navController = LocalNavController.current
    val id = navController.currentBackStackEntry
        ?.arguments
        ?.getString("id")
        ?.toInt()

    if (id == null) {
        navController.popBackStack()
        return
    }

    LaunchedEffect(id) {
        viewModel.loadSet(id.toString())
    }

    val wordsSet by viewModel.wordsSet.collectAsState()

    if (wordsSet == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading...")
        }
    } else {
        ShowSetScreenContent(wordsSet!!)
    }
}

@Composable
fun ShowSetScreenContent(wordsSet: WordsSetEntity) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = wordsSet.title,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn {
            items(wordsSet.words) { word ->
                Text(
                    text = "${word.term}: ${word.definition}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}
