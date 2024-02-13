package com.madteam.split.ui.screens.expensedetail.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.stringResource
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
import com.madteam.split.ui.theme.SplitTheme

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
    }
}

@Preview
@Composable
fun ExpenseDetailContentPreview() {
    ExpenseDetailScreen(navController = rememberNavController())
}