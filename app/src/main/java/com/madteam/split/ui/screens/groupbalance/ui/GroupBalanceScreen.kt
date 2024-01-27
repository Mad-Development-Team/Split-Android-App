package com.madteam.split.ui.screens.groupbalance.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.madteam.split.ui.theme.DSBottomNavigation
import com.madteam.split.ui.theme.SplitTheme
import com.madteam.split.utils.ui.BackPressHandler

@Composable
fun GroupBalanceScreen(
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
            GroupBalanceContent()
        }
    }
}

@Composable
fun GroupBalanceContent() {
    Text(text = "Group balance")
}

@Preview
@Composable
fun GroupBalanceScreenPreview() {
    GroupBalanceScreen(
        navController = rememberNavController()
    )
}