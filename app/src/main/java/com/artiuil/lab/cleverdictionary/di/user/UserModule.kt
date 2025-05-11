package com.artiuil.lab.cleverdictionary.di.user

import com.artiuil.lab.cleverdictionary.domain.repository.user.UserRepository
import com.artiuil.lab.cleverdictionary.domain.repository.user.UserRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): UserRepository = UserRepositoryImpl(
        firebaseAuth = firebaseAuth,
        firestore = firestore
    )
}