package com.madteam.split.ui.screens.signup.email.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import com.madteam.split.ui.navigation.Screens
import com.madteam.split.ui.screens.signup.email.state.SignUpUIState
import com.madteam.split.ui.screens.signup.email.viewmodel.SignUpViewModel
import com.madteam.split.ui.theme.DSBasicTextField
import com.madteam.split.ui.theme.DSCheckBoxTextWithLink
import com.madteam.split.ui.theme.DSEmailTextField
import com.madteam.split.ui.theme.DSPasswordTextField
import com.madteam.split.ui.theme.PrimaryLargeButton
import com.madteam.split.ui.theme.SecondaryLargeButton
import com.madteam.split.ui.theme.SplitTheme

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val state by viewModel.signUpUIState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = SplitTheme.colors.neutral.backgroundExtraWeak
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            SignUpScreenContent(
                state = state,
                navigateBack = navController::popBackStack
            )
        }
    }
}

@Composable
fun SignUpScreenContent(
    state: SignUpUIState,
    navigateBack: () -> Unit
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
            IconButton(onClick = { navigateBack() }) {
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
                text = stringResource(id = R.string.welcome),
                style = SplitTheme.typography.display.m,
                color = SplitTheme.colors.neutral.textTitle,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = stringResource(id = R.string.sign_up_description),
                style = SplitTheme.typography.body.l,
                color = SplitTheme.colors.neutral.textBody,
                textAlign = TextAlign.Justify
            )
            Spacer(modifier = Modifier.size(24.dp))
            DSBasicTextField(
                value = state.name,
                onValueChange = { /* TODO:  */},
                placeholder = R.string.enter_your_name
            )
            DSEmailTextField(
                value = state.email,
                onValueChange = { /* TODO:  */},
                placeholder = R.string.enter_your_email
            )
            DSPasswordTextField(
                value = state.password,
                onValueChange = { /* TODO:  */},
                placeholder = R.string.enter_your_password
            )
            DSPasswordTextField(
                value = state.confirmPassword,
                onValueChange = { /* TODO:  */},
                placeholder = R.string.repeat_password
            )
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(24.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DSCheckBoxTextWithLink(
                checked = true,
                onCheckedChange = { /*TODO*/ },
                text = R.string.accept_terms_and_policy,
                link = "https://www.google.com"
            )
            Spacer(modifier = Modifier.size(48.dp))
            PrimaryLargeButton(
                onClick = {},
                text = R.string.continue_text
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                modifier = Modifier
                    .clickable { /* TODO: Navigate to sign in with email */ },
                text = stringResource(id = R.string.already_have_an_account),
                style = SplitTheme.typography.textLink.l,
                color = SplitTheme.colors.neutral.textLinkDefault
            )
        }
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(
        navController = rememberNavController()
    )
}