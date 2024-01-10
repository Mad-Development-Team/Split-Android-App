package com.madteam.split.ui.screens.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madteam.split.data.repository.AuthenticationRepository
import com.madteam.split.ui.screens.splash.state.SplashUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _state: MutableStateFlow<SplashUIState> = MutableStateFlow(SplashUIState())
    val state: StateFlow<SplashUIState> = _state

    init {
        checkIfUserIsAuthenticated()
    }

    private fun checkIfUserIsAuthenticated() {
        viewModelScope.launch {
            val result = authenticationRepository.authenticate()
            _state.value = _state.value.copy(
                isAuthenticated = result
            )
            onReadyToGo()
        }
    }

    private fun onReadyToGo() {
        _state.value = _state.value.copy(
            isReadyToGo = true
        )
    }

}