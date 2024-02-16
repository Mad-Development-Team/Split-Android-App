package com.madteam.split.ui.screens.groupbalance.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.madteam.split.R
import com.madteam.split.ui.navigation.Screens
import com.madteam.split.ui.screens.group.viewmodel.GroupViewModel
import com.madteam.split.ui.screens.groupbalance.state.GroupBalanceUIEvent
import com.madteam.split.ui.screens.groupbalance.viewmodel.GroupBalanceViewModel
import com.madteam.split.ui.screens.groupinfo.state.GroupInfoUIEvent
import com.madteam.split.ui.theme.DSBottomNavigation
import com.madteam.split.ui.theme.GroupNavigationTopAppBar
import com.madteam.split.ui.theme.GroupsListModalBottomSheet
import com.madteam.split.ui.theme.SplitTheme
import com.madteam.split.utils.ui.BackPressHandler
import com.madteam.split.utils.ui.navigateWithPopUpTo

@Composable
fun GroupBalanceScreen(
    navController: NavController,
    viewModel: GroupBalanceViewModel = hiltViewModel(),
    groupViewModel: GroupViewModel = hiltViewModel(),
) {
    BackPressHandler {
        //Do nothing on back press
    }

    val commonState by groupViewModel.state.collectAsStateWithLifecycle()
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.groupsModalIsVisible) {
        GroupsListModalBottomSheet(
            groupsList = commonState.userGroups,
            currentGroupId = commonState.currentGroupId!!,
            onClose = {
                viewModel.onEvent(
                    GroupBalanceUIEvent.ShowGroupsModal(
                        show = false
                    )
                )
            },
            onGroupSelected = {},
            onNavigateHomeSelected = {
                GroupInfoUIEvent.ShowGroupsModal(
                    show = false
                )
                navController.navigateWithPopUpTo(
                    route = Screens.MyGroupsScreen.route,
                    popUpTo = Screens.GroupInfoScreen.route,
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
                        GroupBalanceUIEvent.ShowGroupsModal(
                            show = true
                        )
                    )
                }
            )
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            GroupBalanceContent()
        }
    }
}

@Composable
fun GroupBalanceContent() {
    Spacer(modifier = Modifier.padding(24.dp))
    Text(
        modifier = Modifier
            .padding(horizontal = 24.dp),
        text = stringResource(id = R.string.members_balance),
        style = SplitTheme.typography.heading.m,
        color = SplitTheme.colors.neutral.textTitle
    )
}

@Preview
@Composable
fun GroupBalanceScreenPreview() {
    GroupBalanceScreen(
        navController = rememberNavController()
    )
}