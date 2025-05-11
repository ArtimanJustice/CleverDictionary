package com.artiuil.lab.cleverdictionary.ui.navigation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign


data class TopBarConfig(
    val title: String = "",
    val navIcon: (@Composable () -> Unit)? = null,
    val actions: (@Composable RowScope.() -> Unit)? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar() {
    val config = LocalTopBarConfig.current.value
    TopAppBar(
        navigationIcon = { config.navIcon?.invoke() },
        title = {
            Text(
                text = config.title,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        actions = { config.actions?.invoke(this) }
    )
}