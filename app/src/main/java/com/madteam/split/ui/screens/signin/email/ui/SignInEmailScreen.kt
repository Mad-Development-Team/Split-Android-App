package com.madteam.split.ui.screens.signin.email.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.madteam.split.ui.screens.signin.email.state.SignInEmailUIEvent
import com.madteam.split.ui.screens.signin.email.state.SignInEmailUIState
import com.madteam.split.ui.screens.signin.email.viewmodel.SignInEmailViewModel
import com.madteam.split.ui.theme.DSTextField
import com.madteam.split.ui.theme.PrimaryLargeButton
import com.madteam.split.ui.theme.SplitTheme

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
                navigateBack = navController::popBackStack
            )
        }
    }

}

@Composable
fun SignInEmailContent(
    state: SignInEmailUIState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
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
            DSTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.emailValue,
                placeholder = R.string.email_placeholder,
                onValueChange = { onEmailChanged(it) }
            )
            DSTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.passwordValue,
                placeholder = R.string.password_placeholder,
                onValueChange = { onPasswordChanged(it) }
            )
            Text(
                modifier = Modifier
                    .padding(start = 4.dp),
                text = stringResource(id = R.string.forgot_password),
                style = SplitTheme.typography.textLink.s,
                color = SplitTheme.colors.neutral.textLinkDefault,
            )
            Spacer(modifier = Modifier.size(24.dp))
            PrimaryLargeButton(
                onClick = { /*TODO*/ },
                text = R.string.continue_text
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
                color = SplitTheme.colors.neutral.textLinkDefault
            )
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