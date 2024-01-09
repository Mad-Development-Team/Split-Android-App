package com.madteam.split.ui.screens.myuser.state

sealed class MyUserUIEvent {
    data class OnShowSignOutDialogStateChanged(val state: Boolean) : MyUserUIEvent()
    data class OnShowSharedInfoMessageStateChanged(val state: Boolean) : MyUserUIEvent()
    data object OnSignOutConfirmedClick : MyUserUIEvent()
}
