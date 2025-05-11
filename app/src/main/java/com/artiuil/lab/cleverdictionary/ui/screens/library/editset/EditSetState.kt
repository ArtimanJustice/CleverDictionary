package com.artiuil.lab.cleverdictionary.ui.screens.library.editset

import com.artiuil.lab.cleverdictionary.domain.entity.words.WordsSetEntity
import com.artiuil.lab.cleverdictionary.domain.repository.words.EditSetError

sealed class EditSetState {
    object Loading : EditSetState()
    data class Success(val wordsSet: WordsSetEntity) : EditSetState()
    data class Error(val error: EditSetError) : EditSetState()
}