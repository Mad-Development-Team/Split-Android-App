package com.madteam.split.ui.screens.groupinfo.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.madteam.split.R
import com.madteam.split.ui.theme.DSBottomNavigation
import com.madteam.split.ui.theme.SecondaryLargeButton
import com.madteam.split.ui.theme.SplitTheme
import com.madteam.split.utils.ui.BackPressHandler

@Composable
fun GroupInfoScreen(
    navController: NavController,
) {
    BackPressHandler {
        //Do nothing on back press
    }
    Scaffold(
        containerColor = SplitTheme.colors.neutral.backgroundExtraWeak,
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