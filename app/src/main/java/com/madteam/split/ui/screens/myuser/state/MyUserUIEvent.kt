package com.madteam.split.ui.screens.myuser.state

import android.net.Uri

sealed class MyUserUIEvent {
    data class OnShowSignOutDialogStateChanged(val state: Boolean) : MyUserUIEvent()
    data class OnShowSharedInfoMessageStateChanged(val state: Boolean) : MyUserUIEvent()
    data class OnShowProfileImageModalStateChanged(val state: Boolean) : MyUserUIEvent()
    data class OnShowSettingsModalStateChanged(val state: Boolean) : MyUserUIEvent()
    data class OnShowErrorMessageStateChanged(val state: Boolean) : MyUserUIEvent()
    data class OnShowChooseAvatarDialogStateChanged(val state: Boolean) : MyUserUIEvent()
    data class OnShowExitDialogStateChanged(val state: Boolean) : MyUserUIEvent()
    data class OnAvatarImageSelected(val index: Int) : MyUserUIEvent()
    data class OnProfileImageSelectedFromDevice(val uri: Uri) : MyUserUIEvent()
    data class OnNameChanged(val name: String) : MyUserUIEvent()
    data object OnSignOutConfirmedClick : MyUserUIEvent()
    data object OnDeleteProfileImageClick : MyUserUIEvent()
    data object OnSaveInfoClick : MyUserUIEvent()
}
