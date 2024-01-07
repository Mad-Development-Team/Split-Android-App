package com.madteam.split.ui.screens.signin.email.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.madteam.split.R
import com.madteam.split.data.model.AuthResult
import com.madteam.split.ui.navigation.Screens
import com.madteam.split.ui.screens.signin.email.state.SignInEmailUIEvent
import com.madteam.split.ui.screens.signin.email.state.SignInEmailUIState
import com.madteam.split.ui.screens.signin.email.viewmodel.SignInEmailViewModel
import com.madteam.split.ui.theme.DSEmailTextField
import com.madteam.split.ui.theme.DSPasswordTextField
import com.madteam.split.ui.theme.ErrorDialog
import com.madteam.split.ui.theme.LoadingDialog
import com.madteam.split.ui.theme.PrimaryLargeButton
import com.madteam.split.ui.theme.SplitTheme
import com.madteam.split.utils.navigateWithPopUpTo

@Composable
fun SignInEmailScreen(
    viewModel: SignInEmailViewModel = hiltViewModel(),
    navController: NavController
) {

    val state by viewModel.signInEmailUIState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = SplitTheme.colors.neutral.backgroundExtraWeak
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            SignInEmailContent(
                state = state,
                onEmailChanged = { email ->
                    viewModel.onEvent(SignInEmailUIEvent.OnEmailChanged(email))
                },
                onPasswordChanged = { password ->
                    viewModel.onEvent(SignInEmailUIEvent.OnPasswordChanged(password))
                },
                onSignInClicked = {
                    viewModel.onEvent(SignInEmailUIEvent.OnSignInClicked)
                },
                navigateBack = navController::popBackStack,
                popUpToGroups = {
                    navController.navigateWithPopUpTo(
                        route = Screens.MyGroupsScreen.route,
                        popUpTo = Screens.WelcomeScreen.route,
                        inclusive = true
                    )
                },
                popUpToSignUp = {
                    navController.navigateWithPopUpTo(
                        route = Screens.SignUpScreen.route,
                        popUpTo = Screens.WelcomeScreen.route
                    )
                },
                setErrorDialogState = { state ->
                    viewModel.onEvent(SignInEmailUIEvent.OnErrorDialogStateChanged(state))
                },
                setErrorFieldsState = { state ->
                    viewModel.onEvent(SignInEmailUIEvent.OnErrorFieldsStateChanged(state))
                },
                navigateTo = navController::navigate
            )
        }
    }

}

@Composable
fun SignInEmailContent(
    state: SignInEmailUIState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onSignInClicked: () -> Unit,
    navigateBack: () -> Unit,
    popUpToGroups: () -> Unit,
    popUpToSignUp: () -> Unit,
    setErrorDialogState: (Boolean) -> Unit,
    setErrorFieldsState: (Boolean) -> Unit,
    navigateTo: (String) -> Unit
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
                text = stringResource(id = R.string.welcome_back),
                style = SplitTheme.typography.display.m,
                color = SplitTheme.colors.neutral.textTitle,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = stringResource(id = R.string.welcome_back_email_password_description),
                style = SplitTheme.typography.body.l,
                color = SplitTheme.colors.neutral.textBody,
                textAlign = TextAlign.Justify
            )
            Spacer(modifier = Modifier.size(24.dp))
            DSEmailTextField(
                value = state.emailValue,
                onValueChange = { onEmailChanged(it) },
                placeholder = R.string.enter_your_email,
                isError = state.isFieldsOnErrorState,
                enabled = true,
            )
            DSPasswordTextField(
                value = state.passwordValue,
                onValueChange = { onPasswordChanged(it) },
                placeholder = R.string.enter_your_password,
                isError = state.isFieldsOnErrorState,
                enabled = true,
                imeAction = ImeAction.Done
            )
            Text(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { navigateTo(Screens.ForgotPasswordScreen.route) },
                text = stringResource(id = R.string.forgot_password),
                style = SplitTheme.typography.textLink.s,
                color = SplitTheme.colors.neutral.textLinkDefault,
            )
            Spacer(modifier = Modifier.size(24.dp))
            PrimaryLargeButton(
                onClick = {
                    onSignInClicked()
                },
                text = R.string.continue_text,
                enabled = state.isEmailValid && state.passwordValue.isNotEmpty() && !state.isFieldsOnErrorState
            )
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 24.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.not_registered_yet),
                style = SplitTheme.typography.textLink.l,
                color = SplitTheme.colors.neutral.textLinkDefault,
                modifier = Modifier.clickable { popUpToSignUp() }
            )
        }
    }

    if (state.isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SplitTheme.colors.neutral.backgroundHeavy.copy(alpha = 0.3f)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadingDialog()
        }
    }

    if (state.isErrorDialog) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SplitTheme.colors.neutral.backgroundHeavy.copy(alpha = 0.3f)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val errorTitle = if (state.authResult is AuthResult.Unauthorized) {
                stringResource(id = R.string.wrong_credentials_title)
            } else {
                stringResource(id = R.string.generic_error_title)
            }

            val errorText = if (state.authResult is AuthResult.Unauthorized) {
                stringResource(id = R.string.wrong_credentials_text)
            } else {
                stringResource(id = R.string.generic_error_text)
            }

            val errorButton = if (state.authResult is AuthResult.Unauthorized) {
                R.string.try_again
            } else {
                R.string.ok
            }

            ErrorDialog(
                setShowDialog = {
                    setErrorDialogState(it)
                },
                errorTitle = errorTitle,
                errorText = errorText,
                errorButton = errorButton
            )
        }
    }

    LaunchedEffect(state.authResult) {
        when (state.authResult) {
            is AuthResult.Authorized -> {
                popUpToGroups()
            }

            is AuthResult.Unauthorized -> {
                setErrorDialogState(true)
                setErrorFieldsState(true)
            }

            is AuthResult.UnknownError -> {
                setErrorDialogState(true)
            }

            else -> {
                //Do nothing
            }
        }
    }
}

@Preview
@Composable
fun SignInEmailScreenPreview() {
    SignInEmailScreen(
        navController = rememberNavController()
    )
}