package com.madteam.split.ui.screens.forgotpassword.viewmodel

import androidx.lifecycle.ViewModel
import com.madteam.split.ui.screens.forgotpassword.state.ForgotPasswordUIEvent
import com.madteam.split.ui.screens.forgotpassword.state.ForgotPasswordUIState
import com.madteam.split.ui.utils.validateEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(

) : ViewModel() {

    private val _forgotPasswordUIState = MutableStateFlow(ForgotPasswordUIState())
    val forgotPasswordUIState: StateFlow<ForgotPasswordUIState> = _forgotPasswordUIState

    fun onEvent(event: ForgotPasswordUIEvent) {
        when (event) {
            is ForgotPasswordUIEvent.EmailChanged -> {
                onEmailChanged(event.email)
            }
        }
    }

    private fun onEmailChanged(email: String) {
        _forgotPasswordUIState.value = _forgotPasswordUIState.value.copy(
            email = email,
            isEmailValid = validateEmail(email)
        )
    }


}