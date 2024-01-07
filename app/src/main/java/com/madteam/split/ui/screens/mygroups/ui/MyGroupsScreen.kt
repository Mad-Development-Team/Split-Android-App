package com.madteam.split.ui.screens.mygroups.ui

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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.madteam.split.R
import com.madteam.split.ui.navigation.Screens
import com.madteam.split.ui.screens.mygroups.state.MyGroupsUIEvent
import com.madteam.split.ui.screens.mygroups.viewmodel.MyGroupsViewModel
import com.madteam.split.ui.theme.PrimaryLargeButton
import com.madteam.split.ui.theme.SecondaryLargeButton
import com.madteam.split.ui.theme.SplitTheme
import com.madteam.split.utils.navigateWithPopUpTo

@Composable
fun MyGroupsScreen(
    navController: NavController,
    viewModel: MyGroupsViewModel = hiltViewModel()
) {

    Scaffold(
        containerColor = SplitTheme.colors.neutral.backgroundExtraWeak
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            MyGroupsContent(
                onCreateNewGroupClick = {
                    viewModel.onEvent(MyGroupsUIEvent.OnCreateNewGroupClick)
                    navController.navigateWithPopUpTo(
                        route = Screens.WelcomeScreen.route,
                        popUpTo = Screens.MyGroupsScreen.route,
                        inclusive = true
                    )
                }
            )
        }
    }
}

@Composable
fun MyGroupsContent(
    onCreateNewGroupClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        MyGroupsTopBar()
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
                onCreateNewGroupClick()
            },
            text = R.string.create_a_new_group
        )
    }
}

@Composable
fun MyGroupsTopBar() {
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
            Icon(
                modifier = Modifier
                    .size(48.dp),
                imageVector = Icons.Default.AccountCircle,
                tint = SplitTheme.colors.neutral.iconHeavy,
                contentDescription = null
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