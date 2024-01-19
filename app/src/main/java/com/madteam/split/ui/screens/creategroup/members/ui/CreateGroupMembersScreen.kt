package com.madteam.split.ui.screens.creategroup.members.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.madteam.split.R
import com.madteam.split.domain.model.Member
import com.madteam.split.ui.navigation.Screens
import com.madteam.split.ui.screens.creategroup.members.state.CreateGroupMembersUIEvent
import com.madteam.split.ui.screens.creategroup.members.state.CreateGroupMembersUIState
import com.madteam.split.ui.screens.creategroup.members.viewmodel.CreateGroupMembersViewModel
import com.madteam.split.ui.theme.AddMemberDialog
import com.madteam.split.ui.theme.AddMembersHorizontalList
import com.madteam.split.ui.theme.ErrorDialog
import com.madteam.split.ui.theme.ErrorMessage
import com.madteam.split.ui.theme.LoadingDialog
import com.madteam.split.ui.theme.PrimaryLargeButton
import com.madteam.split.ui.theme.SecondaryLargeButton
import com.madteam.split.ui.theme.SplitTheme
import com.madteam.split.utils.ui.navigateWithPopUpTo

private const val MAXIMUM_MEMBERS_PER_GROUP = 10

@Composable
fun CreateGroupMembersScreen(
    navController: NavController,
    viewModel: CreateGroupMembersViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        containerColor = SplitTheme.colors.neutral.backgroundExtraWeak
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            CreateGroupMembersContent(
                state = state,
                navigateBack = {
                    navController.popBackStack()
                },
                popUpTo = { route ->
                    navController.navigateWithPopUpTo(
                        inclusive = true,
                        route = route,
                        popUpTo = Screens.MyGroupsScreen.route
                    )
                },
                onNextClick = {
                    viewModel.onEvent(CreateGroupMembersUIEvent.OnNextClick)
                },
                onShowAddMemberDialogChanged = { state ->
                    viewModel.onEvent(CreateGroupMembersUIEvent.OnShowAddMemberDialogChanged(state))
                },
                onNewMemberNameChanged = { name ->
                    viewModel.onEvent(CreateGroupMembersUIEvent.OnNewMemberNameChanged(name))
                },
                onAddNewMemberClicked = {
                    viewModel.onEvent(CreateGroupMembersUIEvent.OnAddMemberClicked)
                },
                onDeleteSelectedMember = {
                    viewModel.onEvent(CreateGroupMembersUIEvent.OnDeleteSelectedMember)
                },
                onMemberSelected = { member ->
                    viewModel.onEvent(CreateGroupMembersUIEvent.OnMemberSelected(member))
                },
                onShowErrorDialogChanged = { state ->
                    viewModel.onEvent(CreateGroupMembersUIEvent.OnShowErrorDialogChanged(state))
                }
            )
        }
    }
}

@Composable
fun CreateGroupMembersContent(
    state: CreateGroupMembersUIState,
    navigateBack: () -> Unit,
    popUpTo: (String) -> Unit,
    onNextClick: () -> Unit,
    onShowAddMemberDialogChanged: (Boolean) -> Unit,
    onNewMemberNameChanged: (String) -> Unit,
    onAddNewMemberClicked: () -> Unit,
    onDeleteSelectedMember: () -> Unit,
    onMemberSelected: (Member) -> Unit,
    onShowErrorDialogChanged: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = {
                navigateBack()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = SplitTheme.colors.neutral.iconHeavy,
                    contentDescription = stringResource(id = R.string.icon_back_description)
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = stringResource(id = R.string.add_members),
                style = SplitTheme.typography.display.m,
                color = SplitTheme.colors.neutral.textTitle,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = stringResource(id = R.string.add_members_description),
                style = SplitTheme.typography.body.l,
                color = SplitTheme.colors.neutral.textBody,
                textAlign = TextAlign.Justify
            )
            Spacer(modifier = Modifier.size(24.dp))
            Text(
                text = stringResource(id = R.string.group_members),
                style = SplitTheme.typography.heading.m,
                color = SplitTheme.colors.neutral.textTitle,
            )
        }
        Column {
            Spacer(modifier = Modifier.size(16.dp))
            AddMembersHorizontalList(
                memberList = state.membersList,
                memberSelected = state.memberSelected,
                onDeleteSelectedMember = {
                    onDeleteSelectedMember()
                },
                onMemberSelected = {
                    onMemberSelected(it)
                }
            )
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            SecondaryLargeButton(
                onClick = {
                    onShowAddMemberDialogChanged(true)
                },
                text = R.string.add_member,
                enabled = state.membersList.size < MAXIMUM_MEMBERS_PER_GROUP
            )
            AnimatedVisibility(visible = state.membersList.size >= MAXIMUM_MEMBERS_PER_GROUP) {
                Column {
                    Spacer(modifier = Modifier.size(8.dp))
                    ErrorMessage(
                        titleText = R.string.maximum_members_reached_error_title,
                        messageText = R.string.maximum_members_reached_error
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 24.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                PrimaryLargeButton(
                    onClick = {
                        onNextClick()
                    },
                    text = R.string.create_group,
                    enabled = state.membersList.size in 2..MAXIMUM_MEMBERS_PER_GROUP
                )
            }
        }
    }

    LaunchedEffect(state.createGroupSuccess) {
        if (state.createGroupSuccess) {
            popUpTo(Screens.CreateGroupInviteScreen.route)
        }
    }

    if (state.showAddMemberDialog) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SplitTheme.colors.neutral.backgroundHeavy.copy(alpha = 0.3f)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AddMemberDialog(
                setShowDialog = { onShowAddMemberDialogChanged(it) },
                newMemberName = state.newMemberName,
                onNewMemberNameChange = {
                    onNewMemberNameChanged(it)
                },
                isNameValid = state.isNewMemberNameValid,
                onContinueClick = {
                    onAddNewMemberClicked()
                },
                errorText = state.nameErrorText
            )
        }
    }

    if (state.showDialogError) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SplitTheme.colors.neutral.backgroundHeavy.copy(alpha = 0.3f)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ErrorDialog(
                setShowDialog = {
                    onShowErrorDialogChanged(it)
                },
                errorText = if (state.errorMessage != null) {
                    stringResource(id = state.errorMessage)
                } else {
                    null
                },
                onContinueClick = {
                    navigateBack()
                }
            )
        }
    }

    if (state.showLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SplitTheme.colors.neutral.backgroundHeavy.copy(alpha = 0.3f)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadingDialog()
        }
    }
}

@Preview
@Composable
fun CreateGroupMembersScreenPreview() {
    CreateGroupMembersScreen(
        rememberNavController()
    )
}