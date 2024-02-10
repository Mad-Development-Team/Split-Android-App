package com.madteam.split.ui.screens.groupexpenses.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.madteam.split.R
import com.madteam.split.domain.model.Expense
import com.madteam.split.domain.model.Group
import com.madteam.split.domain.model.Member
import com.madteam.split.domain.model.MemberExpense
import com.madteam.split.domain.model.PaidByExpense
import com.madteam.split.ui.navigation.Screens
import com.madteam.split.ui.screens.group.state.GroupUIState
import com.madteam.split.ui.screens.group.viewmodel.GroupViewModel
import com.madteam.split.ui.screens.groupexpenses.state.GroupExpensesUIEvent
import com.madteam.split.ui.screens.groupexpenses.state.GroupExpensesUIState
import com.madteam.split.ui.screens.groupexpenses.viewmodel.GroupExpensesViewModel
import com.madteam.split.ui.theme.AmountTextView
import com.madteam.split.ui.theme.DSBottomNavigation
import com.madteam.split.ui.theme.DefaultFloatingButton
import com.madteam.split.ui.theme.GroupNavigationTopAppBar
import com.madteam.split.ui.theme.GroupsListModalBottomSheet
import com.madteam.split.ui.theme.MembersListItemList
import com.madteam.split.ui.theme.SmallSecondaryButton
import com.madteam.split.ui.theme.SplitTheme
import com.madteam.split.ui.utils.formatDateBasedOnLocale
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
            expenses = commonState.groupExpenses,
            groupInfo = commonState.userGroups.first { it.id == commonState.currentGroupId }
        )
    }
}

@Composable
fun GroupExpensesList(
    expenses: List<Expense>,
    groupInfo: Group,
) {
    val sortedExpenses = expenses.sortedByDescending { it.date }
    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(sortedExpenses) { _, expense ->
            ExpenseItem(
                expense = expense,
                groupInfo = groupInfo
            )
        }
    }
}

@Composable
fun ExpenseItem(
    expense: Expense,
    groupInfo: Group,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(100.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = SplitTheme.colors.primary.backgroundWeak
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 2.dp
        )
    ) {
        val context = LocalContext.current
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
            Text(
                text = formatDateBasedOnLocale(
                    dateString = expense.date,
                    context = context
                ),
                modifier = Modifier
                    .constrainAs(expenseDate) {
                        end.linkTo(parent.end)
                        top.linkTo(expenseTitle.top)
                        bottom.linkTo(expenseTitle.bottom)
                    },
                style = SplitTheme.typography.body.m,
                color = SplitTheme.colors.neutral.textBody
            )
            Text(
                text = expense.description.toString(),
                modifier = Modifier
                    .constrainAs(expenseDescription) {
                        start.linkTo(parent.start)
                        top.linkTo(expenseTitle.bottom)

                    }
                    .widthIn(max = 250.dp),
                style = SplitTheme.typography.body.m,
                color = SplitTheme.colors.neutral.textBody,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            AmountTextView(
                modifier = Modifier
                    .constrainAs(expenseTotalAmount) {
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(parent.top)
                    },
                amount = expense.totalAmount,
                currency = expense.currency,
                color = SplitTheme.colors.neutral.textTitle,
            )
            Row(
                modifier = Modifier
                    .constrainAs(paidBySection) {
                        start.linkTo(expenseTypeIcon.start)
                        bottom.linkTo(parent.bottom)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.paid_by),
                    style = SplitTheme.typography.body.s,
                    color = SplitTheme.colors.neutral.textBody
                )
                Spacer(modifier = Modifier.size(4.dp))
                MembersListItemList(
                    members = getMembersWhoPaid(expense.paidBy, groupInfo),
                )
            }
            Row(
                modifier = Modifier
                    .constrainAs(forWhomSection) {
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.for_label),
                    style = SplitTheme.typography.body.s,
                    color = SplitTheme.colors.neutral.textBody
                )
                Spacer(modifier = Modifier.size(4.dp))
                MembersListItemList(
                    members = getMembersForWhomExpenseWasPaid(expense.forWhom, groupInfo),
                )
            }
        }
    }
}

fun getMembersWhoPaid(
    paidByList: List<PaidByExpense>,
    groupInfo: Group,
): List<Member> {
    return paidByList.mapNotNull { paidBy ->
        groupInfo.members.find { it.id == paidBy.memberId }
    }
}

fun getMembersForWhomExpenseWasPaid(
    forWhomList: List<MemberExpense>,
    groupInfo: Group,
): List<Member> {
    return forWhomList.mapNotNull { forWhom ->
        groupInfo.members.find { it.id == forWhom.memberId }
    }
}
