package com.artiuil.lab.cleverdictionary.domain.usecase.words

import com.artiuil.lab.cleverdictionary.domain.entity.words.WordsSetEntity
import com.artiuil.lab.cleverdictionary.domain.repository.words.WordsRepository

class GetAllSetsUseCase(private val repository: WordsRepository) {
    suspend operator fun invoke(): List<WordsSetEntity> {
        return repository.getAllSets()
    }
}
