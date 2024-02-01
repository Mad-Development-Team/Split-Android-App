package com.madteam.split.ui.screens.createexpense.state

sealed class CreateExpenseUIEvent {
    data class OnTitleChanged(val title: String) : CreateExpenseUIEvent()
}
