package com.madteam.split.ui.screens.groupinfo.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun GroupInfoScreen(
    navController: NavController,
) {
}

@Composable
fun GroupInfoContent() {
}

@Preview
@Composable
fun GroupInfoScreenPreview() {
    GroupInfoScreen(
        navController = rememberNavController()
    )
}