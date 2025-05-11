package com.artiuil.lab.cleverdictionary.domain.repository.user

import com.artiuil.lab.cleverdictionary.domain.entity.user.UserEntity

interface UserRepository {
    suspend fun saveUser(user: UserEntity)
    suspend fun getUser(): UserEntity?
    suspend fun updateUser(fields: Map<String, Any?>)
}