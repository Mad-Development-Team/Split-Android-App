package com.madteam.split.ui.screens.createexpense.ui

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Done
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.madteam.split.R
import com.madteam.split.domain.model.Currency
import com.madteam.split.ui.navigation.Screens
import com.madteam.split.ui.screens.createexpense.state.CreateExpenseUIEvent
import com.madteam.split.ui.screens.createexpense.state.CreateExpenseUIState
import com.madteam.split.ui.screens.createexpense.viewmodel.CreateExpenseViewModel
import com.madteam.split.ui.theme.BigIconButton
import com.madteam.split.ui.theme.CurrenciesDialog
import com.madteam.split.ui.theme.DSBasicTextField
import com.madteam.split.ui.theme.DSCurrencyTextField
import com.madteam.split.ui.theme.DSDatePickerTextField
import com.madteam.split.ui.theme.DefaultFloatingButton
import com.madteam.split.ui.theme.ExpenseTypeDialog
import com.madteam.split.ui.theme.SmallEmojiButton
import com.madteam.split.ui.theme.SplitTheme
import com.madteam.split.utils.ui.BackPressHandler
import com.madteam.split.utils.ui.getEmojiByName
import com.madteam.split.utils.ui.getFlagByCurrency
import com.madteam.split.utils.ui.navigateWithPopUpTo
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Date

@Composable
fun CreateExpenseScreen(
    viewModel: CreateExpenseViewModel = hiltViewModel(),
    navController: NavController,
) {
    BackPressHandler {
        //Do nothing on back press
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = SplitTheme.colors.neutral.backgroundExtraWeak,
        floatingActionButton = {
            DefaultFloatingButton(
                icon = Icons.Outlined.Done,
                onClick = {}
            )
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            CreateExpenseContent(
                state = state,
                onExpenseTitleChanged = { title ->
                    viewModel.onEvent(
                        CreateExpenseUIEvent.OnTitleChanged(title)
                    )
                },
                onExpenseDescriptionChanged = { description ->
                    viewModel.onEvent(
                        CreateExpenseUIEvent.OnDescriptionChanged(description)
                    )
                },
                onExpenseAmountChanged = { amount ->
                    viewModel.onEvent(
                        CreateExpenseUIEvent.OnAmountChanged(amount)
                    )
                },
                onDatePickerDialogShowChanged = { show ->
                    viewModel.onEvent(
                        CreateExpenseUIEvent.OnDatePickerDialogShowChanged(show)
                    )
                },
                onDatePickerDateSelected = { date ->
                    viewModel.onEvent(
                        CreateExpenseUIEvent.OnDatePickerDateSelected(date)
                    )
                },
                onPaidByMemberSelected = { memberId ->
                    viewModel.onEvent(
                        CreateExpenseUIEvent.OnPaidByMemberSelected(memberId)
                    )
                },
                onMemberNeedsToPaySelected = { memberId ->
                    viewModel.onEvent(
                        CreateExpenseUIEvent.OnNeedsToPayMemberSelected(memberId)
                    )
                },
                onAllMembersNeedsToPaySelected = {
                    viewModel.onEvent(
                        CreateExpenseUIEvent.OnAllMembersNeedsToPaySelected
                    )
                },
                onCurrencySelected = { currency ->
                    viewModel.onEvent(
                        CreateExpenseUIEvent.OnCurrencySelected(currency)
                    )
                },
                onShowCurrenciesDialog = { show ->
                    viewModel.onEvent(
                        CreateExpenseUIEvent.OnCurrencyDialogShowChanged(show)
                    )
                },
                onShowExpenseTypeDialog = { show ->
                    viewModel.onEvent(
                        CreateExpenseUIEvent.OnExpenseTypeDialogShowChanged(show)
                    )
                },
                popUpBack = {
                    navController.navigateWithPopUpTo(
                        route = Screens.GroupExpensesScreen.route,
                        popUpTo = Screens.CreateExpenseScreen.route,
                        inclusive = true
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateExpenseContent(
    state: CreateExpenseUIState,
    onExpenseTitleChanged: (String) -> Unit,
    onExpenseDescriptionChanged: (String) -> Unit,
    onExpenseAmountChanged: (Double) -> Unit,
    onDatePickerDialogShowChanged: (Boolean) -> Unit,
    onDatePickerDateSelected: (String) -> Unit,
    onPaidByMemberSelected: (Int) -> Unit,
    onMemberNeedsToPaySelected: (Int) -> Unit,
    onAllMembersNeedsToPaySelected: () -> Unit,
    onCurrencySelected: (Currency) -> Unit,
    onShowCurrenciesDialog: (Boolean) -> Unit,
    onShowExpenseTypeDialog: (Boolean) -> Unit,
    popUpBack: () -> Unit,
) {
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
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = SplitTheme.colors.neutral.iconHeavy,
                    contentDescription = stringResource(id = R.string.icon_back_description)
                )
            }
            Spacer(modifier = Modifier.size(24.dp))
            Text(
                text = stringResource(id = R.string.add_expense),
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
                isError = state.isTitleError && state.newExpense.title.isNotEmpty(),
                supportingText = if (state.isTitleError && state.newExpense.title.isNotEmpty()) {
                    R.string.expense_title_maximum_length
                } else if (state.newExpense.title.isEmpty()) {
                    R.string.expense_title_required
                } else {
                    null
                },
                value = state.newExpense.title,
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
                image = getEmojiByName(state.newExpense.type.icon)
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
        ) {
            DSBasicTextField(
                value = state.newExpense.description ?: "",
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
                value = state.newExpense.totalAmount.toString(),
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
                image = getFlagByCurrency(state.currencySelected)
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
        ) {
            DSDatePickerTextField(
                value = state.newExpense.date,
                onValueChange = {},
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
            expense = state.newExpense,
            currency = state.groupInfo.currency,
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
            expense = state.newExpense,
            onMemberClick = { member ->
                onMemberNeedsToPaySelected(member.id)
            },
            currency = state.groupInfo.currency,
            onForAllClick = {
                onAllMembersNeedsToPaySelected()
            }
        )
        Spacer(modifier = Modifier.size(16.dp))
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

    if (state.showCurrencyDialog) {
        CurrenciesDialog(
            currencies = state.currencies,
            onCurrencySelected = {
                onCurrencySelected(it)
            },
            onConfirmCurrency = {
                onShowCurrenciesDialog(false)
            },
            selectedCurrency = state.currencySelected,
            onDismiss = { onShowCurrenciesDialog(false) }
        )
    }

    if (state.showExpenseTypeDialog) {
        ExpenseTypeDialog(
            expensesList = state.groupExpenseTypes,
            onDismiss = {
                onShowExpenseTypeDialog(false)
            },
            onExpenseTypeSelected = {},
            onExpenseTypeCreated = {},
            groupId = state.groupInfo.id
        )
    }
}

@Preview
@Composable
fun CreateExpenseScreenPreview() {
    CreateExpenseScreen(
        navController = rememberNavController()
    )
}