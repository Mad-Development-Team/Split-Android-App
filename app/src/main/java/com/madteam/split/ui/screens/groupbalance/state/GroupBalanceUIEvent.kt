package com.madteam.split.ui.screens.groupbalance.state

sealed class GroupBalanceUIEvent {
    data class ShowGroupsModal(val show: Boolean) : GroupBalanceUIEvent()
}
