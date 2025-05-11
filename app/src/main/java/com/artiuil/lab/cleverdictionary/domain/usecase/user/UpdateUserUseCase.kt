package com.artiuil.lab.cleverdictionary.domain.usecase.user

import com.artiuil.lab.cleverdictionary.domain.repository.user.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    /**
     * @param fields a map of "field_name" to newValue
     * Example usage:
     *    updateUserUseCase(mapOf("userName" to "NewName"))
     *    updateUserUseCase(mapOf("avatarImageUrl" to uriString))
     *    updateUserUseCase(mapOf(
     *        "userName" to "NewName",
     *        "avatarImageUrl" to uriString
     *    ))
     */
    suspend operator fun invoke(fields: Map<String, Any?>) {
        repository.updateUser(fields)
    }
}