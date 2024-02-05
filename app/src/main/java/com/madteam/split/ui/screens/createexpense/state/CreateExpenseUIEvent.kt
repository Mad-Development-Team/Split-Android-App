package com.madteam.split.ui.screens.createexpense.state

import com.madteam.split.domain.model.Currency

sealed class CreateExpenseUIEvent {
    data class OnTitleChanged(val title: String) : CreateExpenseUIEvent()
    data class OnDescriptionChanged(val description: String) : CreateExpenseUIEvent()
    data class OnAmountChanged(val amount: Double) : CreateExpenseUIEvent()
    data class OnDatePickerDialogShowChanged(val show: Boolean) : CreateExpenseUIEvent()
    data class OnDatePickerDateSelected(val date: String) : CreateExpenseUIEvent()
    data class OnPaidByMemberSelected(val memberId: Int) : CreateExpenseUIEvent()
    data class OnNeedsToPayMemberSelected(val memberId: Int) : CreateExpenseUIEvent()
    data class OnCurrencyDialogShowChanged(val show: Boolean) : CreateExpenseUIEvent()
    data class OnCurrencySelected(val currency: Currency) : CreateExpenseUIEvent()
    data object OnAllMembersNeedsToPaySelected : CreateExpenseUIEvent()
}
