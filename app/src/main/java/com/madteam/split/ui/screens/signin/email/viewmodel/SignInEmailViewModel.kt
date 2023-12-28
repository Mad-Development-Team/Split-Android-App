package com.madteam.split.ui.screens.signin.email.viewmodel

import androidx.lifecycle.ViewModel
import com.madteam.split.ui.screens.signin.email.state.SignInEmailUIEvent
import com.madteam.split.ui.screens.signin.email.state.SignInEmailUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SignInEmailViewModel @Inject constructor(
) : ViewModel() {

    private val _signInEmailUIState = MutableStateFlow(SignInEmailUIState())
    val signInEmailUIState: StateFlow<SignInEmailUIState> = _signInEmailUIState

    fun onEvent(event: SignInEmailUIEvent) {
        when (event) {
            is SignInEmailUIEvent.OnEmailChanged -> {
                updateEmailValue(event.email)
            }
            is SignInEmailUIEvent.OnPasswordChanged -> {
                updatePasswordValue(event.password)
            }
        }
    }

    private fun updateEmailValue(emailValue: String) {
        _signInEmailUIState.value = _signInEmailUIState.value.copy(
            emailValue = emailValue
        )
    }

    private fun updatePasswordValue(passwordValue: String) {
        _signInEmailUIState.value = _signInEmailUIState.value.copy(
            passwordValue = passwordValue
        )
    }

}

