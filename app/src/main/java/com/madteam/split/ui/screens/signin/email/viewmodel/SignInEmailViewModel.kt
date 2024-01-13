package com.madteam.split.ui.screens.signin.email.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madteam.split.data.model.utils.AuthResult
import com.madteam.split.data.repository.authentication.AuthenticationRepository
import com.madteam.split.ui.screens.signin.email.state.SignInEmailUIEvent
import com.madteam.split.ui.screens.signin.email.state.SignInEmailUIState
import com.madteam.split.ui.utils.validateEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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
                setErrorFieldsState(false)
            }
            is SignInEmailUIEvent.OnPasswordChanged -> {
                updatePasswordValue(event.password)
                setErrorFieldsState(false)
            }
            is SignInEmailUIEvent.OnSignInClicked -> {
                signInIntent()
            }
            is SignInEmailUIEvent.OnErrorDialogStateChanged -> {
                setErrorDialogState(event.state)
            }
            is SignInEmailUIEvent.OnErrorFieldsStateChanged -> {
                setErrorFieldsState(event.state)
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

    private fun updateAuthResult(authResult: AuthResult<Unit>){
        _signInEmailUIState.value = _signInEmailUIState.value.copy(
            authResult = authResult
        )
    }

    private fun setErrorDialogState(state: Boolean){
        _signInEmailUIState.value = _signInEmailUIState.value.copy(
            isErrorDialog = state
        )
    }

    private fun signInIntent(){
        viewModelScope.launch {
            setLoadingState(true)
            val result = authRepository.signIn(
                email = _signInEmailUIState.value.emailValue,
                password = _signInEmailUIState.value.passwordValue
            )
            setLoadingState(false)
            updateAuthResult(result)
        }
    }

    private fun setLoadingState(state: Boolean){
        _signInEmailUIState.value = _signInEmailUIState.value.copy(
            isLoading = state
        )
    }

    private fun setErrorFieldsState(state: Boolean){
        _signInEmailUIState.value = _signInEmailUIState.value.copy(
            isFieldsOnErrorState = state
        )
    }

}

