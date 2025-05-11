package com.artiuil.lab.cleverdictionary.domain.usecase.words

import com.artiuil.lab.cleverdictionary.domain.entity.words.WordsSetEntity
import com.artiuil.lab.cleverdictionary.domain.repository.words.WordsRepository
import kotlinx.coroutines.flow.Flow

class ObserveAllSetsUseCase(private val repository: WordsRepository) {
    operator fun invoke(): Flow<List<WordsSetEntity>> = repository.observeAllSets()
}
