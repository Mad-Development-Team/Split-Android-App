package com.madteam.split.ui.screens.myuser.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madteam.split.R
import com.madteam.split.data.repository.authentication.AuthenticationRepository
import com.madteam.split.data.repository.user.UserRepository
import com.madteam.split.ui.screens.myuser.state.MyUserUIEvent
import com.madteam.split.ui.screens.myuser.state.MyUserUIState
import com.madteam.split.utils.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyUserViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state: MutableStateFlow<MyUserUIState> = MutableStateFlow(MyUserUIState())
    val state: StateFlow<MyUserUIState> = _state

    init {
        getUpdatedUserInfo()
    }

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

            is MyUserUIEvent.OnShowErrorMessageStateChanged -> {
                showErrorMessage(event.state)
            }

            is MyUserUIEvent.OnNameChanged -> {
                onNameChanged(event.name)
            }

            is MyUserUIEvent.OnSaveInfoClick -> {
                saveUserInfo()
            }
        }
    }

    private fun saveUserInfo() {
        viewModelScope.launch {
            showLoading(true)
            val user = userRepository.updateUserInfo(
              _state.value.userInfo
            )
            when (user) {
                is Resource.Success -> {
                    user.data.let {
                        _state.value = _state.value.copy(
                          originalUserInfo = _state.value.userInfo,
                          hasInfoBeenModified = false
                        )
                    }
                    showLoading(false)
                }

                is Resource.Error -> {
                    showErrorMessage(
                      true,
                      R.string.generic_error_text
                    )
                    showLoading(false)
                }

                is Resource.Loading -> {
                    showLoading(true)
                }
            }
        }
    }

    private fun getUpdatedUserInfo() {
        viewModelScope.launch {
            val user = userRepository.getUserInfo(
              update = true
            )
            when (user) {
                is Resource.Success -> {
                    user.data.let {
                        _state.value = _state.value.copy(
                          userInfo = it,
                          originalUserInfo = it
                        )
                    }
                    showLoading(false)
                }

                is Resource.Error -> {
                    showErrorMessage(
                        true,
                        R.string.generic_error_text
                    )
                    showLoading(false)
                }

                is Resource.Loading -> {
                    showLoading(true)
                }
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

    private fun showProfileImageModal(state: Boolean) {
        _state.value = _state.value.copy(showProfileImageModal = state)
    }

    private fun showErrorMessage(state: Boolean, message: Int? = null) {
        _state.value = _state.value.copy(
          showErrorMessage = state,
          errorMessage = message
        )
    }

    private fun showLoading(state: Boolean) {
        _state.value = _state.value.copy(showLoading = state)
    }

    private fun onNameChanged(updatedName: String) {
        _state.value = _state.value.copy(
          userInfo = _state.value.userInfo.copy(
            name = updatedName
          )
        )
        if (_state.value.userInfo != _state.value.originalUserInfo) {
            _state.value = _state.value.copy(hasInfoBeenModified = true)
        }
    }

    private fun onSignOutClick() {
        viewModelScope.launch {
            authenticationRepository.signOut()
            userRepository.deleteAllUserLocalInfo()
        }
    }
}