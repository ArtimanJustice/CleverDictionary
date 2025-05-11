package com.artiuil.lab.cleverdictionary.domain.entity.words

data class WordEntity(
    val id: String = "",
    val term: String,
    val definition: String,
    val imageUrl: String?
)