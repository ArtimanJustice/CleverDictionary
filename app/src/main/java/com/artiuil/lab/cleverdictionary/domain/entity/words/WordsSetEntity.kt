package com.artiuil.lab.cleverdictionary.domain.entity.words

import com.artiuil.lab.cleverdictionary.domain.dto.words.WordInputDto

data class WordsSetEntity(
    val id: String = "",
    val title: String,
    val imageUrl: String? = null,
    val words: List<WordEntity> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val lastOpenedAt: Long = System.currentTimeMillis()
) {
    companion object
}

fun WordsSetEntity.edit(
    newTitle: String,
    wordInputDtos: List<WordInputDto>
): WordsSetEntity = copy(
    title = newTitle,
    words = wordInputDtos.map { input ->
        WordEntity(
            id = "",
            term = input.term,
            definition = input.definition,
            imageUrl = input.remoteImageUrl
        )
    },
    lastOpenedAt = System.currentTimeMillis()
)

fun WordsSetEntity.Companion.new(
    title: String,
    wordInputDtos: List<WordInputDto>
): WordsSetEntity = WordsSetEntity(
    title = title,
    imageUrl = null,
    words = wordInputDtos.map { input ->
        WordEntity(
            term = input.term,
            definition = input.definition,
            imageUrl = input.remoteImageUrl
        )
    }
)
