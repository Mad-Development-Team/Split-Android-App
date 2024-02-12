package com.madteam.split.ui.screens.group.state

sealed class GroupUIEvent {
    data object RetryGetGroupExpenses : GroupUIEvent()
}
