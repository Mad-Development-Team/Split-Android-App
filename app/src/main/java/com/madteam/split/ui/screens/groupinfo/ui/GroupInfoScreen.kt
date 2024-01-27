package com.madteam.split.ui.screens.groupinfo.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.madteam.split.R
import com.madteam.split.ui.navigation.Screens
import com.madteam.split.ui.screens.groupinfo.state.GroupInfoUIEvent
import com.madteam.split.ui.screens.groupinfo.viewmodel.GroupInfoViewModel
import com.madteam.split.ui.theme.DSBottomNavigation
import com.madteam.split.ui.theme.GroupNavigationTopAppBar
import com.madteam.split.ui.theme.GroupsListModalBottomSheet
import com.madteam.split.ui.theme.SecondaryLargeButton
import com.madteam.split.ui.theme.SplitTheme
import com.madteam.split.utils.ui.BackPressHandler
import com.madteam.split.utils.ui.navigateWithPopUpTo

@Composable
fun GroupInfoScreen(
    navController: NavController,
    viewmodel: GroupInfoViewModel = hiltViewModel(),
) {
    BackPressHandler {
        //Do nothing on back press
    }

    val state by viewmodel.state.collectAsStateWithLifecycle()

    if (state.groupsModalIsVisible) {
        GroupsListModalBottomSheet(
            groupsList = state.groupsList,
            currentGroupId = state.currentGroupId,
            onClose = {
                viewmodel.onEvent(
                    GroupInfoUIEvent.ShowGroupsModal(
                        show = false
                    )
                )
            },
            onGroupSelected = {},
            onNavigateHomeSelected = {
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
        topBar = {
            GroupNavigationTopAppBar(
                currentGroup = state.groupsList.first { it.id == state.currentGroupId },
                onExpandClicked = {
                    viewmodel.onEvent(
                        GroupInfoUIEvent.ShowGroupsModal(
                            show = true
                        )
                    )
                }
            )
        },
        bottomBar = {
            DSBottomNavigation(navController = navController)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            GroupInfoContent(
                goBack = navController::popBackStack
            )
        }
    }
}

@Composable
fun GroupInfoContent(
    goBack: () -> Unit,
) {
    SecondaryLargeButton(onClick = { goBack() }, text = R.string.continue_text)
}

@Preview
@Composable
fun GroupInfoScreenPreview() {
    GroupInfoScreen(
        navController = rememberNavController()
    )
}