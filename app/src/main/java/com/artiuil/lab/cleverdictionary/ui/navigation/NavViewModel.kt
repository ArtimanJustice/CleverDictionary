package com.artiuil.lab.cleverdictionary.ui.navigation

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class NavViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _user = MutableStateFlow(firebaseAuth.currentUser)
    val user: StateFlow<FirebaseUser?> = _user

    private val listener = FirebaseAuth.AuthStateListener {
        _user.value = it.currentUser
    }

    init {
        firebaseAuth.addAuthStateListener(listener)
    }

    override fun onCleared() {
        firebaseAuth.removeAuthStateListener(listener)
        super.onCleared()
    }
}
