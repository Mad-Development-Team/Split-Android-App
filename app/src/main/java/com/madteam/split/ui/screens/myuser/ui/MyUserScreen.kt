package com.madteam.split.ui.screens.myuser.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.madteam.split.R
import com.madteam.split.ui.screens.myuser.viewmodel.MyUserViewModel
import com.madteam.split.ui.theme.NavigationAndActionTopAppBar
import com.madteam.split.ui.theme.SplitTheme

@Composable
fun MyUserScreen(
    navController: NavController,
    viewModel: MyUserViewModel = hiltViewModel()
) {
    Scaffold(
        containerColor = SplitTheme.colors.neutral.backgroundExtraWeak,
        topBar = {
            NavigationAndActionTopAppBar(
                onNavigationClick = {
                    navController.popBackStack()
                },
                onActionClick = {
                    //TODO: Not implemented yet
                },
                title = R.string.your_profile
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            MyUserContent()
        }
    }

}

@Composable
fun MyUserContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

    }
}

@Preview
@Composable
fun MyUserScreenPreview() {
    MyUserScreen(
        rememberNavController()
    )
}