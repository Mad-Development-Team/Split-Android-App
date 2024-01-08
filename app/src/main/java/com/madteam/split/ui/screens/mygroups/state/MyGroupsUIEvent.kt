package com.madteam.split.ui.screens.mygroups.state

sealed class MyGroupsUIEvent {
    data object OnCreateNewGroupClick : MyGroupsUIEvent()
}
