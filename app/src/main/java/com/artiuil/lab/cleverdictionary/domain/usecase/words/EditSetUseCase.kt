package com.artiuil.lab.cleverdictionary.domain.usecase.words

import com.artiuil.lab.cleverdictionary.domain.entity.words.WordsSetEntity
import com.artiuil.lab.cleverdictionary.domain.repository.words.WordsRepository

class EditSetUseCase(private val repository: WordsRepository) {
    suspend operator fun invoke(wordsSet: WordsSetEntity) {
        repository.editSet(wordsSet)
    }
}
