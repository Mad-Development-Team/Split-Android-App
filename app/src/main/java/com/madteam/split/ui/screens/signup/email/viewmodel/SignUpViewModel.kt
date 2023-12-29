package com.madteam.split.ui.screens.signup.email.viewmodel

import androidx.lifecycle.ViewModel
import com.madteam.split.ui.screens.signup.email.state.SignUpUIEvent
import com.madteam.split.ui.screens.signup.email.state.SignUpUIState
import com.madteam.split.ui.utils.validateEmail
import com.madteam.split.ui.utils.validateName
import com.madteam.split.ui.utils.validatePassword
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
            is SignUpUIEvent.OnConfirmPasswordChanged -> {
                confirmPasswordChanged(event.confirmPassword)
            }

            is SignUpUIEvent.OnEmailChanged -> {
                emailChanged(event.email)
            }

            is SignUpUIEvent.OnNameChanged -> {
                nameChanged(event.name)
            }

            is SignUpUIEvent.OnPasswordChanged -> {
                passwordChanged(event.password)
            }

            is SignUpUIEvent.OnTermsAndConditionsChecked -> {
                termsAndConditionsCheckedChanged()
            }

            is SignUpUIEvent.OnSignUpClicked -> {
                signUpClicked()
            }
        }
    }

    private fun nameChanged(name: String) {
        _signUpUIState.value = _signUpUIState.value.copy(
            name = name,
            isNameValid = validateName(name)
        )
    }

    private fun emailChanged(email: String) {
        _signUpUIState.value = _signUpUIState.value.copy(
            email = email,
            isEmailValid = validateEmail(email)
        )
    }

    private fun passwordChanged(password: String) {
        _signUpUIState.value = _signUpUIState.value.copy(
            password = password,
            isPasswordValid = validatePassword(password)
        )
    }

    private fun confirmPasswordChanged(confirmPassword: String) {
        _signUpUIState.value = _signUpUIState.value.copy(
            confirmPassword = confirmPassword,
            isConfirmPasswordValid = _signUpUIState.value.password
                    == confirmPassword
                    && _signUpUIState.value.isPasswordValid
        )
    }

    private fun termsAndConditionsCheckedChanged() {
        _signUpUIState.value = _signUpUIState.value.copy(
            isTermsAndConditionsChecked = !_signUpUIState.value.isTermsAndConditionsChecked,
            isTermsAndConditionsError = _signUpUIState.value.isTermsAndConditionsChecked
        )
    }

    private fun signUpClicked(){
        //TODO: Implement sign up logic
    }
}