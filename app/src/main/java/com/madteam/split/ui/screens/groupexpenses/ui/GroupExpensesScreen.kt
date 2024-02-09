package com.madteam.split.ui.screens.groupexpenses.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.madteam.split.R
import com.madteam.split.domain.model.Currency
import com.madteam.split.domain.model.Expense
import com.madteam.split.domain.model.ExpenseType
import com.madteam.split.domain.model.MemberExpense
import com.madteam.split.domain.model.PaidByExpense
import com.madteam.split.ui.navigation.Screens
import com.madteam.split.ui.screens.group.state.GroupUIState
import com.madteam.split.ui.screens.group.viewmodel.GroupViewModel
import com.madteam.split.ui.screens.groupexpenses.state.GroupExpensesUIEvent
import com.madteam.split.ui.screens.groupexpenses.state.GroupExpensesUIState
import com.madteam.split.ui.screens.groupexpenses.viewmodel.GroupExpensesViewModel
import com.madteam.split.ui.theme.DSBottomNavigation
import com.madteam.split.ui.theme.DefaultFloatingButton
import com.madteam.split.ui.theme.GroupNavigationTopAppBar
import com.madteam.split.ui.theme.GroupsListModalBottomSheet
import com.madteam.split.ui.theme.SmallSecondaryButton
import com.madteam.split.ui.theme.SplitTheme
import com.madteam.split.utils.ui.BackPressHandler
import com.madteam.split.utils.ui.getEmojiByName
import com.madteam.split.utils.ui.navigateWithPopUpTo

@Composable
fun GroupExpensesScreen(
    navController: NavController,
    commonViewModel: GroupViewModel = hiltViewModel(),
    viewModel: GroupExpensesViewModel = hiltViewModel(),
) {
    BackPressHandler {
        //Do nothing on back press
    }

    val commonState by commonViewModel.state.collectAsStateWithLifecycle()
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.groupsModalIsVisible) {
        GroupsListModalBottomSheet(
            groupsList = commonState.userGroups,
            currentGroupId = commonState.currentGroupId!!,
            onClose = {
                viewModel.onEvent(
                    GroupExpensesUIEvent.ShowGroupsModal(
                        show = false
                    )
                )
            },
            onGroupSelected = {},
            onNavigateHomeSelected = {
                GroupExpensesUIEvent.ShowGroupsModal(
                    show = false
                )
                navController.navigateWithPopUpTo(
                    route = Screens.MyGroupsScreen.route,
                    popUpTo = Screens.GroupExpensesScreen.route,
                    inclusive = true
                )
            }
        )
    }

    Scaffold(
        containerColor = SplitTheme.colors.neutral.backgroundExtraWeak,
        bottomBar = {
            DSBottomNavigation(navController = navController)
        },
        topBar = {
            GroupNavigationTopAppBar(
                currentGroup = commonState.userGroups.first { it.id == commonState.currentGroupId },
                onExpandClicked = {
                    viewModel.onEvent(
                        GroupExpensesUIEvent.ShowGroupsModal(
                            show = true
                        )
                    )
                }
            )
        },
        floatingActionButton = {
            DefaultFloatingButton(
                icon = Icons.Outlined.Add,
                onClick = {
                    navController.navigate(Screens.CreateExpenseScreen.route)
                },
                contentDescription = stringResource(id = R.string.add_expense)
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            GroupExpensesContent(
                state = state,
                commonState = commonState
            )
        }
    }
}

@Composable
fun GroupExpensesContent(
    state: GroupExpensesUIState,
    commonState: GroupUIState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        GroupExpensesSummarySection(
            values = mapOf(
                R.string.last_day_expenses to 0.0,
                R.string.total_expenses to 0.0,
                R.string.last_week_expenses to 0.0,
            ),
            currency = "€"
        )
        Spacer(modifier = Modifier.size(16.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
        ) {
            itemsIndexed(state.availableFilters) { _, filter ->
                SmallSecondaryButton(
                    buttonText = filter.title,
                    icon = filter.icon,
                    enabled = filter.enabled
                )
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        GroupExpensesList(
            expenses = commonState.groupExpenses
        )
    }
}

@Composable
fun GroupExpensesList(
    expenses: List<Expense>,
) {
}

@Composable
fun ExpenseItem(
    expense: Expense,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = SplitTheme.colors.primary.backgroundWeak
        )
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            val (expenseTypeIcon, expenseTitle, expenseDate, expenseTotalAmount, paidBySection, forWhomSection, expenseDescription) = createRefs()

            Image(
                modifier = Modifier
                    .constrainAs(expenseTypeIcon) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    },
                painter = painterResource(id = getEmojiByName(expense.type.icon)),
                contentDescription = stringResource(id = R.string.expense_type_icon)
            )
            Text(
                text = expense.title,
                modifier = Modifier
                    .constrainAs(expenseTitle) {
                        start.linkTo(expenseTypeIcon.end, margin = 8.dp)
                        top.linkTo(expenseTypeIcon.top)
                        bottom.linkTo(expenseTypeIcon.bottom)
                    },
                style = SplitTheme.typography.heading.xs,
                color = SplitTheme.colors.neutral.textTitle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun ExpenseItemPreview() {
    ExpenseItem(
        expense = Expense(
            id = 1,
            title = "Lengua de fuego",
            description = "para salvar al mundo de la erupción del popocatepeyi",
            totalAmount = 50.00,
            type = ExpenseType(
                id = 34,
                title = "Salvación",
                icon = "emoji_animals_nature_comet",
                group = null
            ),
            paidBy = listOf(
                PaidByExpense(
                    expenseId = 1,
                    paidAmount = 50.00,
                    memberId = 1
                )
            ),
            forWhom = listOf(
                MemberExpense(
                    expenseId = 1,
                    memberId = 1,
                    amount = 12.5
                ),
                MemberExpense(
                    expenseId = 1,
                    memberId = 2,
                    amount = 12.5
                ),
                MemberExpense(
                    expenseId = 1,
                    memberId = 3,
                    amount = 12.5
                ),
                MemberExpense(
                    expenseId = 1,
                    memberId = 4,
                    amount = 12.5
                )
            ),
            images = null,
            paymentMethod = null,
            date = "2024-02-08 17:48:22",
            group = 1,
            currency = Currency(
                currency = "EUR",
                name = "Euro",
                symbol = "€"
            )
        )
    )
}
