package com.madteam.split.ui.screens.creategroup.invite.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.madteam.split.ui.screens.creategroup.invite.viewmodel.CreateGroupInviteViewModel

@Composable
fun CreateGroupInviteScreen(
    navController: NavController,
    viewModel: CreateGroupInviteViewModel = hiltViewModel(),
) {
}

@Composable
fun CreateGroupInviteContent() {
}

@Preview
@Composable
fun CreateGroupInviteScreenPreview() {
    CreateGroupInviteScreen(
        navController = rememberNavController()
    )
}