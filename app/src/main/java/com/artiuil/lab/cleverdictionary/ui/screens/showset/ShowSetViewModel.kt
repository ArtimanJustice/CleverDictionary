package com.artiuil.lab.cleverdictionary.ui.screens.showset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artiuil.lab.cleverdictionary.domain.entity.words.WordsSetEntity
import com.artiuil.lab.cleverdictionary.domain.usecase.words.GetSetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowSetViewModel @Inject constructor(
    private val getSetUseCase: GetSetUseCase
) : ViewModel() {

    private val _wordsSet = MutableStateFlow<WordsSetEntity?>(null)
    val wordsSet: StateFlow<WordsSetEntity?> = _wordsSet.asStateFlow()

    fun loadSet(id: String) {
        viewModelScope.launch {
            val set = getSetUseCase.invoke(id)
            _wordsSet.value = set
        }
    }
}
