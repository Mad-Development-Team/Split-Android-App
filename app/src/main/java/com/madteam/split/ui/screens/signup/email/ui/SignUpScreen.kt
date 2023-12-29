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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.madteam.split.ui.screens.signup.email.state.SignUpUIEvent
import com.madteam.split.ui.screens.signup.email.state.SignUpUIState
import com.madteam.split.ui.screens.signup.email.viewmodel.SignUpViewModel
import com.madteam.split.ui.theme.DSBasicTextField
import com.madteam.split.ui.theme.DSCheckBoxTextWithLink
import com.madteam.split.ui.theme.DSEmailTextField
import com.madteam.split.ui.theme.DSPasswordTextField
import com.madteam.split.ui.theme.PrimaryLargeButton
import com.madteam.split.ui.theme.SplitTheme

private const val POLICIES_URL = "https://www.google.com"

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
                navigateBack = navController::popBackStack,
                navigateTo = navController::navigate,
                nameChanged = { name ->
                    viewModel.onEvent(SignUpUIEvent.OnNameChanged(name))
                },
                emailChanged = { email ->
                    viewModel.onEvent(SignUpUIEvent.OnEmailChanged(email))
                },
                passwordChanged = { password ->
                    viewModel.onEvent(SignUpUIEvent.OnPasswordChanged(password))
                },
                confirmPasswordChanged = { confirmPassword ->
                    viewModel.onEvent(SignUpUIEvent.OnConfirmPasswordChanged(confirmPassword))
                },
                isTermsAndConditionsCheckedChanged = {
                    viewModel.onEvent(SignUpUIEvent.OnTermsAndConditionsChecked)
                }
            )
        }
    }
}

@Composable
fun SignUpScreenContent(
    state: SignUpUIState,
    navigateBack: () -> Unit,
    navigateTo: (String) -> Unit,
    nameChanged: (String) -> Unit,
    emailChanged: (String) -> Unit,
    passwordChanged: (String) -> Unit,
    confirmPasswordChanged: (String) -> Unit,
    isTermsAndConditionsCheckedChanged: () -> Unit,
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
                .verticalScroll(rememberScrollState())
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
                onValueChange = { nameChanged(it) },
                placeholder = R.string.enter_your_name,
                supportingText = if (state.name.isNotEmpty() && !state.isNameValid) {
                    R.string.sign_up_name_supporting_text_rules
                } else {
                    R.string.sign_up_name_supporting_text
                },
                isError = !state.isNameValid && state.name.isNotEmpty(),
                isSuccess = state.isNameValid && state.name.isNotEmpty()
            )
            Spacer(modifier = Modifier.size(8.dp))
            DSEmailTextField(
                value = state.email,
                onValueChange = { emailChanged(it) },
                placeholder = R.string.enter_your_email,
                supportingText = if (state.email.isNotEmpty() && !state.isEmailValid) {
                    R.string.sign_up_email_supporting_text_rules
                } else {
                    R.string.sign_up_email_supporting_text
                },
                isError = !state.isEmailValid && state.email.isNotEmpty(),
                isSuccess = state.isEmailValid && state.email.isNotEmpty()
            )
            Spacer(modifier = Modifier.size(8.dp))
            DSPasswordTextField(
                value = state.password,
                onValueChange = { passwordChanged(it) },
                placeholder = R.string.enter_your_password,
                isError = !state.isPasswordValid && state.password.isNotEmpty(),
                isSuccess = state.isPasswordValid && state.password.isNotEmpty()
            )
            Spacer(modifier = Modifier.size(4.dp))
            DSPasswordTextField(
                value = state.confirmPassword,
                onValueChange = { confirmPasswordChanged(it) },
                placeholder = R.string.repeat_password,
                supportingText = if (state.isPasswordValid
                    && !state.isConfirmPasswordValid
                    && state.confirmPassword.isNotEmpty()
                ) {
                    R.string.sign_up_confirm_password_error
                } else {
                    R.string.sign_up_password_supporting_text_rules
                },
                isError = !state.isConfirmPasswordValid && state.confirmPassword.isNotEmpty(),
                isSuccess = state.isConfirmPasswordValid && state.confirmPassword.isNotEmpty()
            )
            Spacer(modifier = Modifier.size(16.dp))
            DSCheckBoxTextWithLink(
                checked = state.isTermsAndConditionsChecked,
                onCheckedChange = { isTermsAndConditionsCheckedChanged() },
                text = R.string.accept_terms_and_policy,
                link = POLICIES_URL,
                isError = state.isTermsAndConditionsError
            )
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(24.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            PrimaryLargeButton(
                onClick = { /* TODO: Do sign up */ },
                text = R.string.continue_text,
                enabled = state.isNameValid
                        && state.isEmailValid
                        && state.isPasswordValid
                        && state.isConfirmPasswordValid
                        && state.isTermsAndConditionsChecked
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                modifier = Modifier
                    .clickable { navigateTo(Screens.SignInEmailScreen.route) },
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