package com.madteam.split.ui.screens.signin.email.viewmodel

import androidx.lifecycle.ViewModel
import com.madteam.split.data.repository.AuthenticationRepository
import com.madteam.split.ui.screens.signin.email.state.SignInEmailUIEvent
import com.madteam.split.ui.screens.signin.email.state.SignInEmailUIState
import com.madteam.split.ui.utils.validateEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SignInEmailViewModel @Inject constructor(
    private val authRepository: AuthenticationRepository
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
            is SignInEmailUIEvent.OnSignInClicked -> {

            }
        }
    }

    private fun updateEmailValue(emailValue: String) {
        _signInEmailUIState.value = _signInEmailUIState.value.copy(
            emailValue = emailValue,
            isEmailValid = validateEmail(emailValue)
        )
    }

    private fun updatePasswordValue(passwordValue: String) {
        _signInEmailUIState.value = _signInEmailUIState.value.copy(
            passwordValue = passwordValue
        )
    }

}

