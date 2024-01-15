package com.madteam.split.ui.screens.mygroups.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.madteam.split.domain.model.User
import com.madteam.split.ui.navigation.Screens
import com.madteam.split.ui.screens.mygroups.state.MyGroupsUIState
import com.madteam.split.ui.screens.mygroups.viewmodel.MyGroupsViewModel
import com.madteam.split.ui.theme.PrimaryLargeButton
import com.madteam.split.ui.theme.ProfileImage
import com.madteam.split.ui.theme.SecondaryLargeButton
import com.madteam.split.ui.theme.SplitTheme
import com.madteam.split.utils.ui.BackPressHandler

@Composable
fun MyGroupsScreen(
    navController: NavController,
    viewModel: MyGroupsViewModel = hiltViewModel(),
) {

    BackPressHandler {
        //Do nothing on back press
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = SplitTheme.colors.neutral.backgroundExtraWeak
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            MyGroupsContent(
                state = state,
                navigateTo = navController::navigate
            )
        }
    }
}

@Composable
fun MyGroupsContent(
    state: MyGroupsUIState,
    navigateTo: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        MyGroupsTopBar(
            userInfo = state.userInfo,
            navigateTo = navigateTo
        )
        Spacer(modifier = Modifier.size(24.dp))
        PrimaryLargeButton(
            onClick = { /*TODO*/ },
            text = R.string.received_an_invitation
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.or),
            style = SplitTheme.typography.body.xxl,
            color = SplitTheme.colors.neutral.textTitle
        )
        Spacer(modifier = Modifier.size(16.dp))
        SecondaryLargeButton(
            onClick = {
                navigateTo(Screens.CreateGroupInfoScreen.route)
            },
            text = R.string.create_a_new_group
        )
    }
}

@Composable
fun MyGroupsTopBar(
    userInfo: User,
    navigateTo: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.your_groups),
                style = SplitTheme.typography.heading.m,
                color = SplitTheme.colors.neutral.textTitle,
            )
            ProfileImage(
                modifier = Modifier
                    .clickable {
                        navigateTo(Screens.MyUserScreen.route)
                    },
                userInfo = userInfo,
                size = 48
            )
        }

    }
}

@Preview
@Composable
fun MyGroupsScreenPreview() {
    MyGroupsScreen(
        navController = rememberNavController()
    )
}