package com.madteam.split.ui.screens.createexpense.state

sealed class CreateExpenseUIEvent {
    data class OnTitleChanged(val title: String) : CreateExpenseUIEvent()
    data class OnDescriptionChanged(val description: String) : CreateExpenseUIEvent()
    data class OnAmountChanged(val amount: String) : CreateExpenseUIEvent()
}
