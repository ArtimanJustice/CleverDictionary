package com.artiuil.lab.cleverdictionary.domain.usecase.words

import com.artiuil.lab.cleverdictionary.domain.repository.words.WordsRepository

class DeleteSetUseCase(private val repository: WordsRepository) {
    suspend operator fun invoke(id: String) {
        repository.deleteSet(id)
    }
}