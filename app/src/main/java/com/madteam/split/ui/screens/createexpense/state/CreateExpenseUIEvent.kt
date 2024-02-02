package com.madteam.split.ui.screens.createexpense.state

sealed class CreateExpenseUIEvent {
    data class OnTitleChanged(val title: String) : CreateExpenseUIEvent()
    data class OnDescriptionChanged(val description: String) : CreateExpenseUIEvent()
    data class OnAmountChanged(val amount: Double) : CreateExpenseUIEvent()
    data class OnDatePickerDialogShowChanged(val show: Boolean) : CreateExpenseUIEvent()
    data class OnDatePickerDateSelected(val date: String) : CreateExpenseUIEvent()
    data class OnPaidByMemberSelected(val memberId: Int) : CreateExpenseUIEvent()
    data class OnNeedsToPayMemberSelected(val memberId: Int) : CreateExpenseUIEvent()
    data object OnAllMembersNeedsToPaySelected : CreateExpenseUIEvent()
}
