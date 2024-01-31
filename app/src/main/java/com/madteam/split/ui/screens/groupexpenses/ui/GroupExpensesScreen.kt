package com.madteam.split.ui.screens.groupexpenses.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.madteam.split.R
import com.madteam.split.ui.navigation.Screens
import com.madteam.split.ui.screens.group.state.GroupUIState
import com.madteam.split.ui.screens.group.viewmodel.GroupViewModel
import com.madteam.split.ui.screens.groupexpenses.state.GroupExpensesUIEvent
import com.madteam.split.ui.screens.groupexpenses.state.GroupExpensesUIState
import com.madteam.split.ui.screens.groupexpenses.viewmodel.GroupExpensesViewModel
import com.madteam.split.ui.theme.DSBottomNavigation
import com.madteam.split.ui.theme.GroupNavigationTopAppBar
import com.madteam.split.ui.theme.GroupsListModalBottomSheet
import com.madteam.split.ui.theme.SmallSecondaryButton
import com.madteam.split.ui.theme.SplitTheme
import com.madteam.split.utils.ui.BackPressHandler
import com.madteam.split.utils.ui.navigateWithPopUpTo
import kotlin.math.absoluteValue

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
                R.string.last_day_expenses to 123.45,
                R.string.total_expenses to 3461.33,
                R.string.last_week_expenses to 456.0,
            ),
            currency = "€"
        )
        Spacer(modifier = Modifier.size(16.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.size(16.dp))
            }
            itemsIndexed(state.availableFilters) { _, filter ->
                SmallSecondaryButton(
                    buttonText = filter.title,
                    icon = filter.icon,
                    enabled = filter.enabled
                )
            }
            item {
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GroupExpensesSummarySection(
    modifier: Modifier = Modifier,
    values: Map<Int, Double>,
    currency: String,
) {

    var index by remember { mutableStateOf(1) }
    val amountValue = values.values.elementAt(index)
    val indexOfDecimal = amountValue.toString().indexOf('.')
    val decimalPart = amountValue.toString().substring(indexOfDecimal + 1)

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConstraintLayout(
            modifier = modifier
        ) {
            val (blob, amount, chevronLeft, chevronRight) = createRefs()
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
            Row(
                modifier = Modifier
                    .constrainAs(amount) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                AnimatedContent(
                    targetState = amountValue.toInt(),
                    transitionSpec = {
                        if (targetState > initialState) {
                            slideInVertically { -it } togetherWith slideOutVertically { it }
                        } else {
                            slideInVertically { it } togetherWith slideOutVertically { -it }
                        }
                    }, label = ""
                ) { animatedAmountValue ->
                    Text(
                        modifier = Modifier,
                        text = animatedAmountValue.absoluteValue.toString(),
                        color = SplitTheme.colors.neutral.textExtraWeak,
                        style = SplitTheme.typography.display.s
                    )
                }
                AnimatedContent(
                    targetState = decimalPart.toInt(),
                    transitionSpec = {
                        if (targetState > initialState) {
                            slideInVertically { -it } togetherWith slideOutVertically { it }
                        } else {
                            slideInVertically { it } togetherWith slideOutVertically { -it }
                        }
                    }, label = ""
                ) { animatedDecimalValue ->
                    Text(
                        modifier = Modifier
                            .padding(top = 4.dp),
                        text = if (decimalPart == "0" || decimalPart == "00") ""
                        else animatedDecimalValue.toString(),
                        color = SplitTheme.colors.neutral.textExtraWeak,
                        style = SplitTheme.typography.heading.s
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(top = 4.dp),
                    text = currency,
                    color = SplitTheme.colors.neutral.textExtraWeak,
                    style = SplitTheme.typography.heading.s
                )
            }
            IconButton(
                modifier = Modifier
                    .constrainAs(chevronLeft) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(blob.start, 16.dp)
                    },
                onClick = {
                    if (index > 0) {
                        index--
                    } else {
                        index = values.size - 1
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.ChevronLeft,
                    contentDescription = null
                )
            }
            IconButton(
                modifier = Modifier
                    .constrainAs(chevronRight) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(blob.end, 16.dp)
                    },
                onClick = {
                    if (index < 2) {
                        index++
                    } else {
                        index = 0
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.ChevronRight,
                    contentDescription = null
                )
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = stringResource(id = values.keys.elementAt(index)),
            style = SplitTheme.typography.heading.s,
            color = SplitTheme.colors.neutral.textTitle
        )
    }
}
