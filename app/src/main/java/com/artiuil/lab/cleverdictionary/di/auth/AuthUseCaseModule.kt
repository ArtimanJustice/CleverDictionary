package com.artiuil.lab.cleverdictionary.di.auth

import com.artiuil.lab.cleverdictionary.domain.repository.auth.AuthRepository
import com.artiuil.lab.cleverdictionary.domain.usecase.auth.ResetPasswordUseCase
import com.artiuil.lab.cleverdictionary.domain.usecase.auth.SignInUseCase
import com.artiuil.lab.cleverdictionary.domain.usecase.auth.SignInWithGoogleUseCase
import com.artiuil.lab.cleverdictionary.domain.usecase.auth.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthUseCaseModule {

    @Provides
    @Singleton
    fun provideSignInUseCase(repository: AuthRepository): SignInUseCase {
        return SignInUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSignUpUseCase(repository: AuthRepository): SignUpUseCase {
        return SignUpUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSignInWithGoogleUseCase(repository: AuthRepository): SignInWithGoogleUseCase {
        return SignInWithGoogleUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideResetPasswordUseCase(repository: AuthRepository): ResetPasswordUseCase {
        return ResetPasswordUseCase(repository)
    }
}