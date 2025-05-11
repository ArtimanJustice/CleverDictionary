package com.artiuil.lab.cleverdictionary.di.images

import com.artiuil.lab.cleverdictionary.domain.repository.images.ImageStorageRepository
import com.artiuil.lab.cleverdictionary.domain.repository.images.ImageStorageRepositoryFirebaseImpl
import com.artiuil.lab.cleverdictionary.domain.usecase.images.DownloadImageUseCase
import com.artiuil.lab.cleverdictionary.domain.usecase.images.UploadImageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImagesStorageModule {

    @Provides
    @Singleton
    fun provideStorageRepository(): ImageStorageRepository =
        ImageStorageRepositoryFirebaseImpl()

    @Provides
    @Singleton
    fun provideUploadImageUseCase(repository: ImageStorageRepository): UploadImageUseCase =
        UploadImageUseCase(repository)

    @Provides
    @Singleton
    fun provideDownloadImageUseCase(repository: ImageStorageRepository): DownloadImageUseCase =
        DownloadImageUseCase(repository)
}