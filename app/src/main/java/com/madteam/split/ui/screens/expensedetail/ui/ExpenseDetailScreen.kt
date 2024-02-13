package com.madteam.split.ui.screens.expensedetail.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.madteam.split.R
import com.madteam.split.domain.model.Expense
import com.madteam.split.ui.screens.group.state.GroupUIState
import com.madteam.split.ui.screens.group.viewmodel.GroupViewModel
import com.madteam.split.ui.theme.BlobWithAmount
import com.madteam.split.ui.theme.MemberWithAmount
import com.madteam.split.ui.theme.SplitTheme
import com.madteam.split.utils.ui.getEmojiByName

@Composable
fun ExpenseDetailScreen(
    navController: NavController,
    commonViewModel: GroupViewModel = hiltViewModel(),
) {

    val commonState by commonViewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = SplitTheme.colors.neutral.backgroundExtraWeak,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            ExpenseDetailContent(
                commonState = commonState,
                navigateBack = navController::popBackStack,
            )
        }
    }
}

@Composable
fun ExpenseDetailContent(
    commonState: GroupUIState,
    navigateBack: () -> Unit,
) {
    val currentExpense =
        commonState.groupExpenses.find { it.id == commonState.currentExpense } ?: Expense()
    val currentGroup =
        commonState.userGroups.find { it.id == commonState.currentGroupId } ?: return

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navigateBack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = SplitTheme.colors.neutral.iconHeavy,
                    contentDescription = stringResource(id = R.string.icon_back_description)
                )
            }
            Spacer(modifier = Modifier.size(24.dp))
            Text(
                text = stringResource(id = R.string.expense_details),
                style = SplitTheme.typography.heading.m,
                color = SplitTheme.colors.neutral.textTitle,
            )
        }
        Spacer(modifier = Modifier.size(24.dp))
        BlobWithAmount(
            amountValue = currentExpense.totalAmount,
            currency = currentExpense.currency,
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = currentExpense.title,
            style = SplitTheme.typography.heading.l,
            color = SplitTheme.colors.neutral.textTitle,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = getEmojiByName(currentExpense.type.icon)),
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = currentExpense.type.title,
                style = SplitTheme.typography.body.l,
                color = SplitTheme.colors.neutral.textBody,
            )
        }
        Spacer(modifier = Modifier.size(24.dp))
        if (!currentExpense.description.isNullOrEmpty()) {
            Text(
                text = currentExpense.description,
                style = SplitTheme.typography.body.l,
                color = SplitTheme.colors.neutral.textBody,
            )
            Spacer(modifier = Modifier.size(16.dp))
        }
        Text(
            text = stringResource(id = R.string.paid_by),
            style = SplitTheme.typography.heading.m,
            color = SplitTheme.colors.neutral.textTitle,
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = currentExpense.date,
            style = SplitTheme.typography.body.l,
            color = SplitTheme.colors.neutral.textBody,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.size(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            val paidByMembers = currentExpense.paidBy.mapNotNull { paidBy ->
                currentGroup.members.find { it.id == paidBy.memberId }
            }
            items(paidByMembers.size) { index ->
                MemberWithAmount(
                    member = paidByMembers[index],
                    isMemberHighLighted = false,
                    currency = currentExpense.currency,
                    onMemberClick = {},
                    amount = currentExpense.paidBy.find { it.memberId == paidByMembers[index].id }?.paidAmount
                        ?: 0.0
                )
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = stringResource(id = R.string.who_pays),
            style = SplitTheme.typography.heading.m,
            color = SplitTheme.colors.neutral.textTitle,
        )
        Spacer(modifier = Modifier.size(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            val forWhomMembers = currentExpense.forWhom.mapNotNull { forWhom ->
                currentGroup.members.find { it.id == forWhom.memberId }
            }
            items(forWhomMembers.size) { index ->
                MemberWithAmount(
                    member = forWhomMembers[index],
                    isMemberHighLighted = false,
                    currency = currentExpense.currency,
                    onMemberClick = {},
                    amount = currentExpense.forWhom.find { it.memberId == forWhomMembers[index].id }?.amount
                        ?: 0.0
                )
            }
        }
    }
}

@Preview
@Composable
fun ExpenseDetailContentPreview() {
    ExpenseDetailScreen(navController = rememberNavController())
}