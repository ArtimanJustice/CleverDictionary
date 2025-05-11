package com.artiuil.lab.cleverdictionary.domain.usecase.words

import com.artiuil.lab.cleverdictionary.domain.entity.words.WordsSetEntity
import com.artiuil.lab.cleverdictionary.domain.repository.words.WordsRepository

class GetSetUseCase(private val repository: WordsRepository) {
    suspend operator fun invoke(id: String): WordsSetEntity? {
        return repository.getSet(id)
    }
}
