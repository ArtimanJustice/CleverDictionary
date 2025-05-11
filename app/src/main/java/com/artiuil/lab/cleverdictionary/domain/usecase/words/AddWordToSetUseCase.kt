package com.artiuil.lab.cleverdictionary.domain.usecase.words

import com.artiuil.lab.cleverdictionary.domain.entity.words.WordEntity
import com.artiuil.lab.cleverdictionary.domain.repository.words.WordsRepository

class AddWordToSetUseCase(private val repository: WordsRepository) {
    suspend operator fun invoke(setId: Int, word: WordEntity) {
        repository.addWordToSet(setId, word)
    }
}
