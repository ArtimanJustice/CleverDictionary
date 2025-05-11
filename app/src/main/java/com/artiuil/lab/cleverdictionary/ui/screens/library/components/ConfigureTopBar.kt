package com.artiuil.lab.cleverdictionary.ui.screens.library.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.artiuil.lab.cleverdictionary.R
import com.artiuil.lab.cleverdictionary.ui.navigation.components.LocalTopBarConfig
import com.artiuil.lab.cleverdictionary.ui.navigation.components.TopBarConfig

@Composable
fun ConfigureTopBar(
    title: String,
    onClose: () -> Unit,
    onSave: () -> Unit,
    actionsEnabled: Boolean = true
) {
    val context = LocalContext.current
    val topBarConfig = LocalTopBarConfig.current

    topBarConfig.value = TopBarConfig(
        title = title,
        navIcon = {
            IconButton(
                onClick = onClose,
                enabled = actionsEnabled
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = context.getString(R.string.close_button_description)
                )
            }
        },
        actions = {
            IconButton(
                onClick = onSave,
                enabled = actionsEnabled
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = context.getString(R.string.save_set_button_description)
                )
            }
        }
    )
}
