package com.madteam.split.ui.screens.groupexpenses.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.madteam.split.R
import com.madteam.split.ui.navigation.Screens
import com.madteam.split.ui.screens.group.viewmodel.GroupViewModel
import com.madteam.split.ui.screens.groupexpenses.state.GroupExpensesUIEvent
import com.madteam.split.ui.screens.groupexpenses.viewmodel.GroupExpensesViewModel
import com.madteam.split.ui.theme.DSBottomNavigation
import com.madteam.split.ui.theme.GroupNavigationTopAppBar
import com.madteam.split.ui.theme.GroupsListModalBottomSheet
import com.madteam.split.ui.theme.SplitTheme
import com.madteam.split.utils.ui.BackPressHandler
import com.madteam.split.utils.ui.navigateWithPopUpTo
import java.math.BigDecimal

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
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            GroupExpensesContent()
        }
    }
}

@Composable
fun GroupExpensesContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

    }
}

@Composable
fun GroupExpensesSummarySection(
    modifier: Modifier = Modifier,
    values: Map<String, BigDecimal>,
    currency: String,
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val (blob, amount, decimalAmount, currency) = createRefs()
        val totalExpenses = values.values.elementAt(0)
        val lastDay = values.values.elementAt(1)
        val lastWeek = values.values.elementAt(2)
        Image(
            modifier = Modifier
                .constrainAs(blob) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .size(150.dp),
            painter = painterResource(id = R.drawable.blob2),
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .constrainAs(amount) {
                    top.linkTo(blob.top)
                    bottom.linkTo(blob.bottom)
                    start.linkTo(blob.start)
                    end.linkTo(blob.end)
                },
            text = totalExpenses.toInt().toString(),
            color = SplitTheme.colors.neutral.textExtraWeak,
            style = SplitTheme.typography.display.s
        )
    }
}

@Preview
@Composable
fun GroupExpensesSummarySectionPreview() {
    GroupExpensesSummarySection(
        values = mapOf(
            "Total expenses" to BigDecimal(1546.33),
            "Last day" to BigDecimal(54.87),
            "Last week" to BigDecimal(349)
        ),
        currency = "€"
    )
}

@Preview
@Composable
fun GroupExpensesScreenPreview() {
    GroupExpensesScreen(
        navController = rememberNavController()
    )
}