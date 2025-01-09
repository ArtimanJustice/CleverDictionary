package com.artiuil.lab.cleverdictionary.ui.screens.main.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.artiuil.lab.cleverdictionary.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar() {
    CenterAlignedTopAppBar(
        title = { Text("hello") },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@Preview(showBackground = true)
@Composable
fun MainTopBarPreview() {
    AppTheme {
        MainTopBar()
    }
}
