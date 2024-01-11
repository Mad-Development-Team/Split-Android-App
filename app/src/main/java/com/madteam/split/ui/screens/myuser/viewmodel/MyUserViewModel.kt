package com.madteam.split.ui.screens.myuser.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madteam.split.data.repository.authentication.AuthenticationRepository
import com.madteam.split.ui.screens.myuser.state.MyUserUIEvent
import com.madteam.split.ui.screens.myuser.state.MyUserUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyUserViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _state: MutableStateFlow<MyUserUIState> = MutableStateFlow(MyUserUIState())
    val state: StateFlow<MyUserUIState> = _state

    fun onEvent(event: MyUserUIEvent) {
        when (event) {
            is MyUserUIEvent.OnShowSignOutDialogStateChanged -> {
                showSignOutDialog(event.state)
            }
            is MyUserUIEvent.OnShowSharedInfoMessageStateChanged -> {
                showSharedInfoMessage(event.state)
            }
            is MyUserUIEvent.OnSignOutConfirmedClick -> {
                onSignOutClick()
            }
            is MyUserUIEvent.OnShowProfileImageModalStateChanged -> {
                showProfileImageModal(event.state)
            }
            is MyUserUIEvent.OnShowSettingsModalStateChanged -> {
                showSettingsModal(event.state)
            }
        }
    }

    private fun showSignOutDialog(state: Boolean) {
        _state.value = _state.value.copy(showLogOutDialog = state)
    }

    private fun showSettingsModal(state: Boolean) {
        _state.value = _state.value.copy(showSettingsModal = state)
    }

    private fun showSharedInfoMessage(state: Boolean) {
        _state.value = _state.value.copy(showSharedInfoMessage = state)
    }

    private fun showProfileImageModal(state: Boolean){
        _state.value = _state.value.copy(showProfileImageModal = state)
    }

    private fun onSignOutClick() {
        viewModelScope.launch {
            authenticationRepository.signOut()
        }
    }

}