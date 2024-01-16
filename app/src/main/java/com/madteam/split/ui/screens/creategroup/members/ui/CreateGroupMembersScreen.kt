package com.madteam.split.ui.screens.creategroup.members.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.madteam.split.ui.screens.creategroup.members.viewmodel.CreateGroupMembersViewModel
import com.madteam.split.ui.theme.SplitTheme

@Composable
fun CreateGroupMembersScreen(
    navController: NavController,
    viewModel: CreateGroupMembersViewModel = hiltViewModel(),
) {
    Scaffold(
        containerColor = SplitTheme.colors.neutral.backgroundExtraWeak
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            CreateGroupMembersContent(
            )
        }
    }
}

@Composable
fun CreateGroupMembersContent() {
}

@Preview
@Composable
fun CreateGroupMembersScreenPreview() {
    CreateGroupMembersScreen(
        rememberNavController()
    )
}