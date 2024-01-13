package com.madteam.split.ui.screens.myuser.state

sealed class MyUserUIEvent {
    data class OnShowSignOutDialogStateChanged(val state: Boolean) : MyUserUIEvent()
    data class OnShowSharedInfoMessageStateChanged(val state: Boolean) : MyUserUIEvent()
    data class OnShowProfileImageModalStateChanged(val state: Boolean) : MyUserUIEvent()
    data class OnShowSettingsModalStateChanged(val state: Boolean) : MyUserUIEvent()
    data class OnShowErrorMessageStateChanged(val state: Boolean) : MyUserUIEvent()
    data class OnNameChanged(val name: String) : MyUserUIEvent()
    data object OnSignOutConfirmedClick : MyUserUIEvent()
    data object OnSaveInfoClick : MyUserUIEvent()
}
