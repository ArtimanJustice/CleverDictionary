package com.artiuil.lab.cleverdictionary.di.user

import com.artiuil.lab.cleverdictionary.domain.repository.user.UserRepository
import com.artiuil.lab.cleverdictionary.domain.usecase.user.GetUserUseCase
import com.artiuil.lab.cleverdictionary.domain.usecase.user.SaveUserUseCase
import com.artiuil.lab.cleverdictionary.domain.usecase.user.UpdateUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserUseCaseModule {

    @Provides
    @Singleton
    fun provideGetAvatarImageUriUseCase(repository: UserRepository): SaveUserUseCase {
        return SaveUserUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetUserNameUseCase(repository: UserRepository): GetUserUseCase {
        return GetUserUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateUserUseCase(repository: UserRepository): UpdateUserUseCase {
        return UpdateUserUseCase(repository)
    }
}