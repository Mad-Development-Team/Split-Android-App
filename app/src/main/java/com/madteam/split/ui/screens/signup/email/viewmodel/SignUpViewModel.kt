package com.madteam.split.ui.screens.signup.email.viewmodel

import androidx.lifecycle.ViewModel
import com.madteam.split.ui.screens.signup.email.state.SignUpUIEvent
import com.madteam.split.ui.screens.signup.email.state.SignUpUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(

) : ViewModel() {

    private val _signUpUIState = MutableStateFlow(SignUpUIState())
    val signUpUIState: StateFlow<SignUpUIState> = _signUpUIState

    fun onEvent(event: SignUpUIEvent) {
        when (event) {

            else -> {}
        }
    }
}