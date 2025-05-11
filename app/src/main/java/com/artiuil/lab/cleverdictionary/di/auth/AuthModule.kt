package com.artiuil.lab.cleverdictionary.di.auth

import com.artiuil.lab.cleverdictionary.domain.repository.auth.AuthRepository
import com.artiuil.lab.cleverdictionary.domain.repository.auth.AuthRepositoryFirebaseImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthRepository = AuthRepositoryFirebaseImpl(
        firebaseAuth = firebaseAuth,
        firestore = firestore
    )
}