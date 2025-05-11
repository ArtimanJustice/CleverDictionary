package com.artiuil.lab.cleverdictionary.utils

import android.content.pm.ActivityInfo
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect

@Composable
fun LockOrientation(orientation: Int) {
    val activity = LocalActivity.current
    DisposableEffect(activity) {
        activity?.requestedOrientation = orientation
        onDispose {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }
}
