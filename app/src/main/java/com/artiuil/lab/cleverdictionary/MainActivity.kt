package com.artiuil.lab.cleverdictionary

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.artiuil.lab.cleverdictionary.ui.navigation.NavApp
import com.artiuil.lab.cleverdictionary.ui.theme.AppTheme
import com.artiuil.lab.cleverdictionary.utils.LockOrientation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LockOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            AppTheme {
                NavApp()
            }
        }
    }
}
