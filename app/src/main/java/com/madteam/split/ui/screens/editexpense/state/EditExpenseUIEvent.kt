package com.madteam.split.ui.screens.editexpense.state

import com.madteam.split.domain.model.Expense
import com.madteam.split.domain.model.ExpenseType
import com.madteam.split.domain.model.Group

sealed class EditExpenseUIEvent {
    data class OnLoadExpense(val expense: Expense, val groupInfo: Group) : EditExpenseUIEvent()
    data class OnExpenseTitleChange(val title: String) : EditExpenseUIEvent()
    data class OnShowExpenseTypeDialog(val state: Boolean) : EditExpenseUIEvent()
    data class OnExpenseDescriptionChanged(val description: String) : EditExpenseUIEvent()
    data class OnExpenseAmountChanged(val amount: Double) : EditExpenseUIEvent()
    data class OnShowCurrenciesDialog(val state: Boolean) : EditExpenseUIEvent()
    data class OnDatePickerDialogShowChanged(val state: Boolean) : EditExpenseUIEvent()
    data class OnPaidByMemberSelected(val memberId: Int) : EditExpenseUIEvent()
    data class OnMemberNeedsToPaySelected(val memberId: Int) : EditExpenseUIEvent()
    data class OnDatePickerDateSelected(val date: String) : EditExpenseUIEvent()
    data class OnExpenseTypeSelected(val expenseType: ExpenseType) : EditExpenseUIEvent()
    data class OnExpenseTypeCreated(val expenseType: ExpenseType) : EditExpenseUIEvent()
    data class OnErrorDialogShowChanged(val state: Boolean) : EditExpenseUIEvent()
    data class OnDeleteDialogShowChanged(val state: Boolean) : EditExpenseUIEvent()
    data object OnAllMembersNeedsToPaySelected : EditExpenseUIEvent()
    data object OnDeleteExpense : EditExpenseUIEvent()
    data object OnUpdateExpense : EditExpenseUIEvent()
}
