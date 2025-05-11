package com.artiuil.lab.cleverdictionary.domain.entity.user

data class UserEntity(
    val id: String = "",
    val email: String = "",
    val userName: String = "",
    val avatarImageUrl: String? = null,
)
