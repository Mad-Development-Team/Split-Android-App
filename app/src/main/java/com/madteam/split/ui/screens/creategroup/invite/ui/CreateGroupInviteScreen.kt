package com.madteam.split.ui.screens.creategroup.invite.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
import com.madteam.split.ui.navigation.Screens
import com.madteam.split.ui.screens.creategroup.invite.state.CreateGroupInviteUIState
import com.madteam.split.ui.screens.creategroup.invite.viewmodel.CreateGroupInviteViewModel
import com.madteam.split.ui.theme.PrimaryLargeButton
import com.madteam.split.ui.theme.SecondaryLargeButton
import com.madteam.split.ui.theme.SplitTheme
import com.madteam.split.utils.ui.BackPressHandler
import com.madteam.split.utils.ui.navigateWithPopUpTo

@Composable
fun CreateGroupInviteScreen(
    navController: NavController,
    viewModel: CreateGroupInviteViewModel = hiltViewModel(),
) {
    BackPressHandler {
        //Do nothing
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
            CreateGroupInviteContent(
                state = state,
                onFinishClick = {
                    navController.navigateWithPopUpTo(
                        route = Screens.MyGroupsScreen.route,
                        inclusive = true,
                        popUpTo = Screens.CreateGroupInviteScreen.route
                    )
                }
            )
        }
    }
}

@Composable
fun CreateGroupInviteContent(
    state: CreateGroupInviteUIState,
    onFinishClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.done_exclamation),
                    style = SplitTheme.typography.display.m,
                    color = SplitTheme.colors.neutral.textTitle,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.size(8.dp))
                Icon(
                    modifier = Modifier
                        .size(32.dp),
                    tint = Color.Unspecified,
                    painter = painterResource(id = R.drawable.emoji_partying_face),
                    contentDescription = stringResource(
                        id = R.string.emoji_partying_face_description
                    )
                )
            }
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = stringResource(id = R.string.invite_description),
                style = SplitTheme.typography.body.l,
                color = SplitTheme.colors.neutral.textBody,
                textAlign = TextAlign.Justify
            )
            Spacer(modifier = Modifier.size(24.dp))
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = state.group!!.inviteCode,
                        style = SplitTheme.typography.display.l,
                        color = SplitTheme.colors.neutral.textTitle,
                        maxLines = 1,
                        textAlign = TextAlign.Center
                    )
                    IconButton(onClick = {
                        //TODO: Implement copying the code to the clipboard
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.ContentCopy,
                            tint = SplitTheme.colors.neutral.iconHeavy,
                            contentDescription = stringResource(
                                id = R.string.icon_copy_description
                            ),
                        )
                    }
                }
                Spacer(modifier = Modifier.size(8.dp))
                SecondaryLargeButton(
                    onClick = {
                        //TODO: Implement sending a link with the code to directly join the group
                    },
                    text = R.string.or_send_by_link
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            PrimaryLargeButton(
                onClick = {
                    onFinishClick()
                },
                text = R.string.go_to_my_groups,
                enabled = true
            )
        }
    }
}

@Preview
@Composable
fun CreateGroupInviteScreenPreview() {
    CreateGroupInviteScreen(
        navController = rememberNavController()
    )
}