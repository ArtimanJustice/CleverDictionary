package com.artiuil.lab.cleverdictionary.domain.repository.words

import com.artiuil.lab.cleverdictionary.domain.entity.words.WordEntity
import com.artiuil.lab.cleverdictionary.domain.entity.words.WordsSetEntity
import kotlinx.coroutines.flow.Flow

interface WordsRepository {

    suspend fun getSet(id: String): WordsSetEntity?

    suspend fun getAllSets(): List<WordsSetEntity>

    suspend fun deleteSet(id: String)

    suspend fun createSet(wordsSet: WordsSetEntity)

    suspend fun addWordToSet(setId: Int, word: WordEntity)

    suspend fun editSet(wordsSet: WordsSetEntity)

    fun observeAllSets(): Flow<List<WordsSetEntity>>
}
