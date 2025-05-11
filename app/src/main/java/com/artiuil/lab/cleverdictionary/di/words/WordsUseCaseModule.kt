package com.artiuil.lab.cleverdictionary.di.words

import com.artiuil.lab.cleverdictionary.domain.repository.words.WordsRepository
import com.artiuil.lab.cleverdictionary.domain.usecase.words.AddWordToSetUseCase
import com.artiuil.lab.cleverdictionary.domain.usecase.words.CreateSetUseCase
import com.artiuil.lab.cleverdictionary.domain.usecase.words.DeleteSetUseCase
import com.artiuil.lab.cleverdictionary.domain.usecase.words.EditSetUseCase
import com.artiuil.lab.cleverdictionary.domain.usecase.words.GetAllSetsUseCase
import com.artiuil.lab.cleverdictionary.domain.usecase.words.GetSetUseCase
import com.artiuil.lab.cleverdictionary.domain.usecase.words.ObserveAllSetsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object WordsUseCaseModule {

    @Provides
    fun provideGetSetUseCase(repository: WordsRepository): GetSetUseCase {
        return GetSetUseCase(repository)
    }

    @Provides
    fun provideGetAllSetsUseCase(repository: WordsRepository): GetAllSetsUseCase {
        return GetAllSetsUseCase(repository)
    }

    @Provides
    fun provideDeleteSetUseCase(repository: WordsRepository): DeleteSetUseCase {
        return DeleteSetUseCase(repository)
    }

    @Provides
    fun provideCreateSetUseCase(repository: WordsRepository): CreateSetUseCase {
        return CreateSetUseCase(repository)
    }

    @Provides
    fun provideAddWordToSetUseCase(repository: WordsRepository): AddWordToSetUseCase {
        return AddWordToSetUseCase(repository)
    }

    @Provides
    fun provideObserveAllSetsUseCase(repository: WordsRepository): ObserveAllSetsUseCase {
        return ObserveAllSetsUseCase(repository)
    }

    @Provides
    fun provideEditSetUseCase(repository: WordsRepository): EditSetUseCase {
        return EditSetUseCase(repository)
    }
}
