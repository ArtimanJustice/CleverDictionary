package com.artiuil.lab.cleverdictionary.ui.screens.library.createset

sealed class CreateSetState {
    object Idle : CreateSetState()
    object Saving : CreateSetState()
    object Error : CreateSetState()
}