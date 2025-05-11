package com.artiuil.lab.cleverdictionary.domain.dto.words

data class WordDto(
    val id: String = "",
    val term: String = "",
    val definition: String = "",
    val imageUrl: String? = null
)