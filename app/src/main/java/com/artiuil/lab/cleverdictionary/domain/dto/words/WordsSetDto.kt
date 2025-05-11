package com.artiuil.lab.cleverdictionary.domain.dto.words

data class WordsSetDto(
    val id: String = "",
    val title: String = "",
    val imageUrl: String? = null,
    val words: List<WordDto>? = null,
    val createdAt: Long = 0,
    val lastOpenedAt: Long = 0
)