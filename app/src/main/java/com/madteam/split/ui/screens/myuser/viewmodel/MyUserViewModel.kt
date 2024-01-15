package com.madteam.split.ui.screens.myuser.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madteam.split.R
import com.madteam.split.data.repository.authentication.AuthenticationRepository
import com.madteam.split.data.repository.storage.StorageRepository
import com.madteam.split.data.repository.user.UserRepository
import com.madteam.split.ui.screens.myuser.state.MyUserUIEvent
import com.madteam.split.ui.screens.myuser.state.MyUserUIState
import com.madteam.split.ui.utils.validateName
import com.madteam.split.utils.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyUserViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val userRepository: UserRepository,
    private val storageRepository: StorageRepository,
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

            is MyUserUIEvent.OnShowChooseAvatarDialogStateChanged -> {
                onShowChooseAvatarDialogClick(event.state)
            }

            is MyUserUIEvent.OnDeleteProfileImageClick -> {
                deleteProfileImage()
            }

            is MyUserUIEvent.OnAvatarImageSelected -> {
                onAvatarImageSelected(event.index)
                onShowChooseAvatarDialogClick(false)
            }

            is MyUserUIEvent.OnProfileImageSelectedFromDevice -> {
                updateUserInfoWithSelectedImageFromDevice(event.uri)
            }
        }
    }

    private fun updateUserInfoWithSelectedImageFromDevice(uri: Uri) {
        _state.value = _state.value.copy(
            userInfo = _state.value.userInfo.copy(
                profileImage = uri.toString()
            ),
            newProfileImageSelectedFromDevice = true
        )
        checkIfInfoHasBeenModified()
    }

    private fun deleteProfileImage() {
        _state.value = _state.value.copy(
            userInfo = _state.value.userInfo.copy(
                profileImage = ""
            ),
            newProfileImageSelectedFromDevice = false
        )
        checkIfInfoHasBeenModified()
    }

    private fun saveUserInfo() {
        viewModelScope.launch {
            showLoading(true)
            if (_state.value.newProfileImageSelectedFromDevice) {
                val newUserImageResult = storageRepository.uploadProfileImage(
                    _state.value.userInfo.profileImage,
                    _state.value.userInfo.id
                )
                val newUserImageUrl: String
                if (newUserImageResult is Resource.Success) {
                    newUserImageUrl = newUserImageResult.data
                    _state.value = _state.value.copy(
                        userInfo = _state.value.userInfo.copy(
                            profileImage = newUserImageUrl
                        )
                    )
                } else {
                    _state.value = _state.value.copy(
                        userInfo = _state.value.userInfo.copy(
                            profileImage = ""
                        ),
                        showErrorMessage = true
                    )
                }
            }
            val user = userRepository.updateUserInfo(
                _state.value.userInfo
            )
            if (_state.value.originalUserInfo.profileImage.isNotBlank()
                && _state.value.userInfo.profileImage.isBlank()) {
                storageRepository.deleteAllProfileUserImages(_state.value.userInfo.id)
                userRepository.removeProfileImage(_state.value.userInfo.id)
            }
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

    private fun onAvatarImageSelected(index: Int) {
        _state.value = _state.value.copy(
            userInfo = _state.value.userInfo.copy(
                profileImage = _state.value.avatarImagesUrlsList[index]
            ),
            newProfileImageSelectedFromDevice = false
        )
        checkIfInfoHasBeenModified()
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
            ),
            nameIsValid = validateName(updatedName)
        )
        checkIfInfoHasBeenModified()
    }

    private fun checkIfInfoHasBeenModified() {
        if (_state.value.userInfo != _state.value.originalUserInfo) {
            _state.value = _state.value.copy(hasInfoBeenModified = true)
        } else {
            _state.value = _state.value.copy(hasInfoBeenModified = false)
        }
    }

    private fun onShowChooseAvatarDialogClick(state: Boolean) {
        if (_state.value.avatarImagesUrlsList.isEmpty()) {
            showLoading(true)
            viewModelScope.launch {
                val avatarImagesUrlsList = storageRepository.getAvatarImagesUrls()
                if (avatarImagesUrlsList is Resource.Success) {
                    _state.value =
                        _state.value.copy(avatarImagesUrlsList = avatarImagesUrlsList.data)
                }
            }
            showLoading(false)
        }
        _state.value = _state.value.copy(showChooseAvatarDialog = state)
    }

    private fun onSignOutClick() {
        viewModelScope.launch {
            authenticationRepository.signOut()
            userRepository.deleteAllUserLocalInfo()
        }
    }
}