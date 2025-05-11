package com.artiuil.lab.cleverdictionary.domain.dto.words

import android.net.Uri

data class WordInputDto(
    val term: String,
    val definition: String,
    val localImageUri: Uri? = null,
    val remoteImageUrl: String? = null
)
