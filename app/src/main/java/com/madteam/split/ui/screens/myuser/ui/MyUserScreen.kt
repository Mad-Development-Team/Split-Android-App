package com.madteam.split.ui.screens.myuser.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.madteam.split.R
import com.madteam.split.domain.model.User
import com.madteam.split.ui.navigation.Screens
import com.madteam.split.ui.screens.myuser.state.MyUserUIEvent
import com.madteam.split.ui.screens.myuser.state.MyUserUIState
import com.madteam.split.ui.screens.myuser.viewmodel.MyUserViewModel
import com.madteam.split.ui.theme.DSBasicTextField
import com.madteam.split.ui.theme.DSEmailTextField
import com.madteam.split.ui.theme.DangerDialog
import com.madteam.split.ui.theme.DangerLargeButton
import com.madteam.split.ui.theme.NavigationAndActionTopAppBar
import com.madteam.split.ui.theme.ProfileImage
import com.madteam.split.ui.theme.SplitTheme
import com.madteam.split.utils.ui.navigateWithPopUpTo

@Composable
fun MyUserScreen(
    navController: NavController,
    viewModel: MyUserViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

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
            MyUserContent(
                state = state,
                onSignOutClick = {
                    viewModel.onEvent(MyUserUIEvent.OnShowSignOutDialogStateChanged(true))
                },
                onSignOutDialogStateChanged = { state ->
                    viewModel.onEvent(MyUserUIEvent.OnShowSignOutDialogStateChanged(state))
                },
                onSignOutConfirmed = {
                    viewModel.onEvent(MyUserUIEvent.OnSignOutConfirmedClick)
                    navController.navigateWithPopUpTo(
                        route = Screens.WelcomeScreen.route,
                        popUpTo = Screens.MyUserScreen.route,
                        inclusive = true
                    )
                }
            )
        }
    }

}

@Composable
fun MyUserContent(
    state: MyUserUIState,
    onSignOutClick: () -> Unit,
    onSignOutDialogStateChanged: (Boolean) -> Unit,
    onSignOutConfirmed: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileImage(
            userInfo = User(
                id = "1",
                name = "John Doe",
                email = "",
                profileImage = ""
            ),
            size = 120
        )
        Spacer(modifier = Modifier.size(36.dp))
        DSEmailTextField(
            value = state.userInfo.email,
            onValueChange = {
                //Not editable
            },
            placeholder = R.string.enter_your_email,
            enabled = false
        )
        DSBasicTextField(
            value = state.userInfo.name,
            onValueChange = {
                //Not implemented yet
            },
            placeholder = R.string.enter_your_name
        )
    }
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        DangerLargeButton(
            onClick = { onSignOutClick() },
            text = R.string.log_out
        )
    }

    if (state.showLogOutDialog) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SplitTheme.colors.neutral.backgroundHeavy.copy(alpha = 0.3f)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DangerDialog(
                setShowDialog = {
                    onSignOutDialogStateChanged(it)
                },
                title = R.string.is_it_a_goodbye,
                text = R.string.log_out_confirm_text,
                cancelButtonText = R.string.cancel,
                continueButtonText = R.string.continue_log_out,
                onContinueClick = {
                    onSignOutConfirmed()
                },
            )
        }
    }
}

@Preview
@Composable
fun MyUserScreenPreview() {
    MyUserScreen(
        rememberNavController()
    )
}