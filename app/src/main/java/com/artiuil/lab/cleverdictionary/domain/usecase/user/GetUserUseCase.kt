package com.artiuil.lab.cleverdictionary.domain.usecase.user

import com.artiuil.lab.cleverdictionary.domain.entity.user.UserEntity
import com.artiuil.lab.cleverdictionary.domain.repository.user.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): UserEntity? {
        return repository.getUser()
    }
}