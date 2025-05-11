package com.artiuil.lab.cleverdictionary.domain.mappers

import com.artiuil.lab.cleverdictionary.domain.dto.words.WordDto
import com.artiuil.lab.cleverdictionary.domain.dto.words.WordsSetDto
import com.artiuil.lab.cleverdictionary.domain.entity.words.WordEntity
import com.artiuil.lab.cleverdictionary.domain.entity.words.WordsSetEntity

fun WordsSetDto.toDomain(): WordsSetEntity {
    return WordsSetEntity(
        id = id,
        title = title,
        imageUrl = imageUrl,
        words = words?.map { it.toDomain() } ?: emptyList(),
        createdAt = createdAt,
        lastOpenedAt = lastOpenedAt
    )
}

fun WordsSetEntity.toDto(): WordsSetDto {
    return WordsSetDto(
        id = id,
        title = title,
        imageUrl = imageUrl,
        words = words.map { it.toDto() },
        createdAt = createdAt,
        lastOpenedAt = lastOpenedAt
    )
}

fun WordDto.toDomain(): WordEntity {
    return WordEntity(
        id = id,
        term = term,
        definition = definition,
        imageUrl = imageUrl
    )
}

fun WordEntity.toDto(): WordDto {
    return WordDto(
        id = id,
        term = term,
        definition = definition,
        imageUrl = imageUrl
    )
}