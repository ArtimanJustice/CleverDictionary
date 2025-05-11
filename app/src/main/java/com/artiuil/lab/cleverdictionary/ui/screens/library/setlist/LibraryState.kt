package com.artiuil.lab.cleverdictionary.ui.screens.library.setlist

import com.artiuil.lab.cleverdictionary.domain.entity.words.WordsSetEntity
import com.artiuil.lab.cleverdictionary.domain.repository.words.LibraryError

sealed class LibraryState {
    object Loading : LibraryState()
    data class Success(val sets: List<WordsSetEntity>) : LibraryState()
    data class Error(val error: LibraryError) : LibraryState()
}