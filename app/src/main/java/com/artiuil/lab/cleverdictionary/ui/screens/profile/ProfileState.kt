package com.artiuil.lab.cleverdictionary.ui.screens.profile

import com.artiuil.lab.cleverdictionary.domain.entity.user.UserEntity

sealed class ProfileState {
    object Loading : ProfileState()
    data class Success(val user: UserEntity) : ProfileState()
    data class Error(val message: String) : ProfileState()
}