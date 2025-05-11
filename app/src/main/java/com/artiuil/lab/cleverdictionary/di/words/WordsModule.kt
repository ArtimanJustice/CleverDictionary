package com.artiuil.lab.cleverdictionary.di.words

import com.artiuil.lab.cleverdictionary.domain.repository.words.WordsRepository
import com.artiuil.lab.cleverdictionary.domain.repository.words.WordsRepositoryFirestoreImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WordsModule {

    @Provides
    @Singleton
    fun provideWordsRepository(
        firebaseFirestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ): WordsRepository = WordsRepositoryFirestoreImpl(firebaseFirestore, firebaseAuth)
}