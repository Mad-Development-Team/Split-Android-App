package com.madteam.split.ui.screens.myuser.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.madteam.split.ui.screens.myuser.state.MyUserUIState
import com.madteam.split.ui.screens.myuser.viewmodel.MyUserViewModel
import com.madteam.split.ui.theme.DSBasicTextField
import com.madteam.split.ui.theme.DSEmailTextField
import com.madteam.split.ui.theme.DangerLargeButton
import com.madteam.split.ui.theme.NavigationAndActionTopAppBar
import com.madteam.split.ui.theme.ProfileImage
import com.madteam.split.ui.theme.SplitTheme

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
                state = state
            )
        }
    }

}

@Composable
fun MyUserContent(
    state: MyUserUIState
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
            onClick = { /*TODO*/ },
            text = R.string.log_out
        )
    }
}

@Preview
@Composable
fun MyUserScreenPreview() {
    MyUserScreen(
        rememberNavController()
    )
}