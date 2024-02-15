package com.madteam.split.ui.screens.editexpense.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.madteam.split.R
import com.madteam.split.domain.model.Expense
import com.madteam.split.domain.model.ExpenseType
import com.madteam.split.ui.navigation.Screens
import com.madteam.split.ui.screens.createexpense.ui.ForWhomMembersList
import com.madteam.split.ui.screens.createexpense.ui.PaidByMembersList
import com.madteam.split.ui.screens.editexpense.state.EditExpenseUIEvent
import com.madteam.split.ui.screens.editexpense.state.EditExpenseUIState
import com.madteam.split.ui.screens.editexpense.viewmodel.EditExpenseViewModel
import com.madteam.split.ui.screens.group.viewmodel.GroupViewModel
import com.madteam.split.ui.theme.BigIconButton
import com.madteam.split.ui.theme.DSBasicTextField
import com.madteam.split.ui.theme.DSCurrencyTextField
import com.madteam.split.ui.theme.DSDatePickerTextField
import com.madteam.split.ui.theme.DangerDialog
import com.madteam.split.ui.theme.DangerLargeButton
import com.madteam.split.ui.theme.DefaultFloatingButton
import com.madteam.split.ui.theme.ErrorDialog
import com.madteam.split.ui.theme.ExpenseTypeDialog
import com.madteam.split.ui.theme.LoadingDialog
import com.madteam.split.ui.theme.SmallEmojiButton
import com.madteam.split.ui.theme.SplitTheme
import com.madteam.split.ui.utils.convertToShortDateFormat
import com.madteam.split.utils.ui.BackPressHandler
import com.madteam.split.utils.ui.getEmojiByName
import com.madteam.split.utils.ui.getFlagByCurrency
import com.madteam.split.utils.ui.navigateWithPopUpTo
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Date

@Composable
fun EditExpenseScreen(
    viewModel: EditExpenseViewModel = hiltViewModel(),
    commonViewModel: GroupViewModel = hiltViewModel(),
    navController: NavController,
) {
    BackPressHandler {
        //Do nothing on back press
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val commonState by commonViewModel.state.collectAsStateWithLifecycle()
    val currentExpense =
        commonState.groupExpenses.find { it.id == commonState.currentExpense } ?: Expense()
    val currentGroup =
        commonState.userGroups.find { it.id == commonState.currentGroupId } ?: return
    val isDifferent = state.expense != currentExpense
    val isAbleToContinue = !state.isLoading && !state.isTitleError && !state.isAmountError &&
        state.expense.paidBy.isNotEmpty() && state.expense.forWhom.isNotEmpty() && isDifferent

    LaunchedEffect(currentExpense, currentGroup) {
        viewModel.onEvent(
            EditExpenseUIEvent.OnLoadExpense(
                expense = currentExpense,
                groupInfo = currentGroup
            )
        )
    }

    Scaffold(
        containerColor = SplitTheme.colors.neutral.backgroundExtraWeak,
        floatingActionButton = {
            if (isAbleToContinue) {
                DefaultFloatingButton(
                    icon = Icons.Outlined.Save,
                    onClick = {
                        viewModel.onEvent(EditExpenseUIEvent.OnUpdateExpense)
                    }
                )
            }
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            EditExpenseContent(
                state = state,
                popUpBack = {
                    navController.navigateWithPopUpTo(
                        route = Screens.ExpenseDetailScreen.route,
                        popUpTo = Screens.EditExpenseScreen.route,
                        inclusive = true
                    )
                },
                popUpBackToGroupExpenses = {
                    navController.navigateWithPopUpTo(
                        route = Screens.GroupExpensesScreen.route,
                        popUpTo = Screens.EditExpenseScreen.route,
                        inclusive = true
                    )
                },
                onExpenseTitleChanged = { title ->
                    viewModel.onEvent(EditExpenseUIEvent.OnExpenseTitleChange(title))
                },
                onShowExpenseTypeDialog = { state ->
                    viewModel.onEvent(EditExpenseUIEvent.OnShowExpenseTypeDialog(state))
                },
                onExpenseDescriptionChanged = { description ->
                    viewModel.onEvent(EditExpenseUIEvent.OnExpenseDescriptionChanged(description))
                },
                onExpenseAmountChanged = { amount ->
                    viewModel.onEvent(EditExpenseUIEvent.OnExpenseAmountChanged(amount))
                },
                onShowCurrenciesDialog = { state ->
                    viewModel.onEvent(EditExpenseUIEvent.OnShowCurrenciesDialog(state))
                },
                onDatePickerDialogShowChanged = { state ->
                    viewModel.onEvent(EditExpenseUIEvent.OnDatePickerDialogShowChanged(state))
                },
                onPaidByMemberSelected = { memberId ->
                    viewModel.onEvent(EditExpenseUIEvent.OnPaidByMemberSelected(memberId))
                },
                onMemberNeedsToPaySelected = { memberId ->
                    viewModel.onEvent(EditExpenseUIEvent.OnMemberNeedsToPaySelected(memberId))
                },
                onAllMembersNeedsToPaySelected = {
                    viewModel.onEvent(EditExpenseUIEvent.OnAllMembersNeedsToPaySelected)
                },
                onDatePickerDateSelected = { date ->
                    viewModel.onEvent(EditExpenseUIEvent.OnDatePickerDateSelected(date))
                },
                onExpenseTypeSelected = { expenseType ->
                    viewModel.onEvent(EditExpenseUIEvent.OnExpenseTypeSelected(expenseType))
                },
                onExpenseTypeCreated = { expenseType ->
                    viewModel.onEvent(EditExpenseUIEvent.OnExpenseTypeCreated(expenseType))
                },
                onErrorDialogShowChanged = { state ->
                    viewModel.onEvent(EditExpenseUIEvent.OnErrorDialogShowChanged(state))
                    navController.navigateWithPopUpTo(
                        route = Screens.ExpenseDetailScreen.route,
                        popUpTo = Screens.EditExpenseScreen.route,
                        inclusive = true
                    )
                },
                onDeleteDialogShowChanged = { state ->
                    viewModel.onEvent(EditExpenseUIEvent.OnDeleteDialogShowChanged(state))
                },
                onDeleteExpense = {
                    viewModel.onEvent(EditExpenseUIEvent.OnDeleteExpense)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditExpenseContent(
    state: EditExpenseUIState,
    popUpBack: () -> Unit,
    popUpBackToGroupExpenses: () -> Unit,
    onExpenseTitleChanged: (String) -> Unit,
    onShowExpenseTypeDialog: (Boolean) -> Unit,
    onExpenseDescriptionChanged: (String) -> Unit,
    onExpenseAmountChanged: (Double) -> Unit,
    onShowCurrenciesDialog: (Boolean) -> Unit,
    onDatePickerDialogShowChanged: (Boolean) -> Unit,
    onPaidByMemberSelected: (Int) -> Unit,
    onMemberNeedsToPaySelected: (Int) -> Unit,
    onAllMembersNeedsToPaySelected: () -> Unit,
    onDatePickerDateSelected: (String) -> Unit,
    onExpenseTypeSelected: (ExpenseType) -> Unit,
    onExpenseTypeCreated: (ExpenseType) -> Unit,
    onErrorDialogShowChanged: (Boolean) -> Unit,
    onDeleteDialogShowChanged: (Boolean) -> Unit,
    onDeleteExpense: () -> Unit,
) {
    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            popUpBackToGroupExpenses()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { popUpBack() }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    tint = SplitTheme.colors.neutral.iconHeavy,
                    contentDescription = stringResource(id = R.string.icon_close_description)
                )
            }
            Spacer(modifier = Modifier.size(24.dp))
            Text(
                text = stringResource(id = R.string.edit_expense),
                style = SplitTheme.typography.heading.m,
                color = SplitTheme.colors.neutral.textTitle,
            )
        }
        Spacer(modifier = Modifier.size(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            DSBasicTextField(
                modifier = Modifier.weight(1f),
                isError = state.isTitleError && state.expense.title.isNotEmpty(),
                supportingText = if (state.isTitleError && state.expense.title.isNotEmpty()) {
                    R.string.expense_title_maximum_length
                } else if (state.expense.title.isEmpty()) {
                    R.string.expense_title_required
                } else {
                    null
                },
                value = state.expense.title,
                onValueChange = {
                    onExpenseTitleChanged(it)
                },
                placeholder = R.string.expense_title
            )
            Spacer(modifier = Modifier.size(8.dp))
            SmallEmojiButton(
                onClick = {
                    onShowExpenseTypeDialog(true)
                },
                image = getEmojiByName(state.expense.type.icon)
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
        ) {
            DSBasicTextField(
                value = state.expense.description ?: "",
                onValueChange = {
                    onExpenseDescriptionChanged(it)
                },
                isError = state.isDescriptionError,
                supportingText = if (state.isDescriptionError) {
                    R.string.expense_description_maximum_length
                } else {
                    null
                },
                placeholder = R.string.expense_description,
                maxLines = 5
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            DSCurrencyTextField(
                modifier = Modifier.weight(1f),
                value = state.expense.totalAmount.toString(),
                onValueChange = {
                    onExpenseAmountChanged(it.toDouble())
                },
                placeholder = R.string.expense_amount,
                supportingText = if (state.isAmountError) {
                    R.string.expense_amount_required
                } else {
                    null
                },
            )
            Spacer(modifier = Modifier.size(8.dp))
            SmallEmojiButton(
                onClick = {
                    onShowCurrenciesDialog(true)
                },
                image = getFlagByCurrency(state.expense.currency)
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
        ) {
            DSDatePickerTextField(
                value = convertToShortDateFormat(state.expense.date),
                onValueChange = {
                    onDatePickerDialogShowChanged(true)
                },
                placeholder = R.string.expense_date,
                onCalendarClick = {
                    onDatePickerDialogShowChanged(true)
                }
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            text = stringResource(id = R.string.add_images),
            style = SplitTheme.typography.heading.m,
        )
        BigIconButton(
            modifier = Modifier
                .padding(start = 24.dp, top = 8.dp),
            icon = Icons.Outlined.Add,
            enabled = true,
            onClick = {}
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            text = stringResource(id = R.string.paid_by),
            style = SplitTheme.typography.heading.m,
        )
        Spacer(modifier = Modifier.size(16.dp))
        PaidByMembersList(
            membersList = state.groupInfo.members,
            expense = state.expense,
            currency = state.expense.currency,
            onMemberClick = { member ->
                onPaidByMemberSelected(member.id)
            }
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            text = stringResource(id = R.string.for_whom),
            style = SplitTheme.typography.heading.m,
        )
        Spacer(modifier = Modifier.size(16.dp))
        ForWhomMembersList(
            membersList = state.groupInfo.members,
            expense = state.expense,
            onMemberClick = { member ->
                onMemberNeedsToPaySelected(member.id)
            },
            currency = state.expense.currency,
            onForAllClick = {
                onAllMembersNeedsToPaySelected()
            }
        )
        Spacer(modifier = Modifier.size(24.dp))
        DangerLargeButton(
            modifier = Modifier.padding(horizontal = 24.dp),
            onClick = {
                onDeleteDialogShowChanged(true)
            },
            text = R.string.delete_expense
        )
        Spacer(modifier = Modifier.size(24.dp))
    }

    if (state.isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SplitTheme.colors.neutral.backgroundHeavy.copy(alpha = 0.3f)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadingDialog()
        }
    }

    if (state.showDatePickerDialog) {
        val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= System.currentTimeMillis()
            }
        })

        val selectedDate = datePickerState.selectedDateMillis?.let { millis ->
            val date = Date(millis).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
            formatter.format(date)
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DatePickerDialog(
                onDismissRequest = { onDatePickerDialogShowChanged(false) },
                confirmButton = {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SplitTheme.colors.primary.backgroundMedium,
                            contentColor = SplitTheme.colors.neutral.textExtraWeak
                        ),
                        onClick = {
                            if (selectedDate != null) {
                                onDatePickerDateSelected(selectedDate)
                            }
                            onDatePickerDialogShowChanged(false)
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.select),
                            style = SplitTheme.typography.body.l,
                        )
                    }
                },
                dismissButton = {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SplitTheme.colors.neutral.backgroundDark,
                            contentColor = SplitTheme.colors.neutral.textExtraWeak
                        ),
                        onClick = {
                            onDatePickerDialogShowChanged(false)
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.dismiss),
                            style = SplitTheme.typography.body.l,
                        )
                    }
                },
            ) {
                DatePicker(
                    state = datePickerState,
                    title = {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 24.dp, vertical = 16.dp),
                            text = stringResource(id = R.string.expense_date),
                            style = SplitTheme.typography.heading.l,
                        )
                    },
                    headline = {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 24.dp),
                            text = stringResource(id = R.string.select_a_date),
                            style = SplitTheme.typography.heading.s,
                        )
                    },
                    colors = DatePickerDefaults.colors(
                        titleContentColor = SplitTheme.colors.neutral.textTitle,
                        headlineContentColor = SplitTheme.colors.neutral.textStrong,
                        selectedDayContentColor = SplitTheme.colors.primary.backgroundStrong,
                        selectedDayContainerColor = SplitTheme.colors.primary.backgroundWeak,
                    )
                )
            }
        }
    }

    if (state.showExpenseTypeDialog) {
        ExpenseTypeDialog(
            expensesList = state.groupExpenseTypes,
            onDismiss = {
                onShowExpenseTypeDialog(false)
            },
            onExpenseTypeSelected = {
                onExpenseTypeSelected(it)
                onShowExpenseTypeDialog(false)
            },
            onExpenseTypeCreated = {
                onExpenseTypeCreated(it)
                onExpenseTypeSelected(it)
                onShowExpenseTypeDialog(false)
            },
            groupId = state.groupInfo.id,
            selectedExpenseType = state.expense.type
        )
    }

    if (state.isError) {
        ErrorDialog(
            setShowDialog = {
                onErrorDialogShowChanged(it)
            },
            onContinueClick = {
                onErrorDialogShowChanged(false)
            }
        )
    }

    if (state.deleteDialog) {
        DangerDialog(
            setShowDialog = {
                onDeleteDialogShowChanged(it)
            },
            title = R.string.are_you_sure,
            text = R.string.delete_expense_description,
            cancelButtonText = R.string.cancel,
            continueButtonText = R.string.continue_text,
            onContinueClick = {
                onDeleteDialogShowChanged(false)
                onDeleteExpense()
            },
        )
    }
}
