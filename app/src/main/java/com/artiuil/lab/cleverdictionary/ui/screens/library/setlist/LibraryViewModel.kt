package com.artiuil.lab.cleverdictionary.ui.screens.library.setlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artiuil.lab.cleverdictionary.domain.repository.words.LibraryError
import com.artiuil.lab.cleverdictionary.domain.usecase.words.DeleteSetUseCase
import com.artiuil.lab.cleverdictionary.domain.usecase.words.ObserveAllSetsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val observeAllSetsUseCase: ObserveAllSetsUseCase,
    private val deleteSetUseCase: DeleteSetUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<LibraryState>(LibraryState.Loading)
    val state: StateFlow<LibraryState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            observeAllSetsUseCase()
                .onStart {
                    if (_state.value !is LibraryState.Success) {
                        _state.value = LibraryState.Loading
                    }
                }
                .catch {
                    _state.value = LibraryState.Error(LibraryError.UnknownError)
                }
                .collect { sets ->
                    _state.value = LibraryState.Success(sets)
                }
        }
    }

    fun deleteSet(id: String) {
        viewModelScope.launch {
            deleteSetUseCase(id)
        }
    }
}
