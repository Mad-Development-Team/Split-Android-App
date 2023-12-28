package com.madteam.split.ui.screens.forgotpassword.ui

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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.madteam.split.R
import com.madteam.split.ui.screens.forgotpassword.state.ForgotPasswordUIEvent
import com.madteam.split.ui.screens.forgotpassword.state.ForgotPasswordUIState
import com.madteam.split.ui.screens.forgotpassword.viewmodel.ForgotPasswordViewModel
import com.madteam.split.ui.theme.DSEmailTextField
import com.madteam.split.ui.theme.PrimaryLargeButton
import com.madteam.split.ui.theme.SplitTheme

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    viewModel: ForgotPasswordViewModel = hiltViewModel()
) {

    val state by viewModel.forgotPasswordUIState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = SplitTheme.colors.neutral.backgroundExtraWeak
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            ForgotPasswordContent(
                state = state,
                onEmailChanged = { viewModel.onEvent(ForgotPasswordUIEvent.EmailChanged(it)) },
                navigateBack = navController::popBackStack
            )
        }
    }

}

@Composable
fun ForgotPasswordContent(
    state: ForgotPasswordUIState,
    onEmailChanged: (String) -> Unit,
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
            val animation by rememberLottieComposition(
                spec = LottieCompositionSpec.RawRes(
                    R.raw.handling_keys_animation
                )
            )
            Text(
                text = stringResource(id = R.string.forgot_password),
                style = SplitTheme.typography.display.m,
                color = SplitTheme.colors.neutral.textTitle,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = stringResource(id = R.string.forgot_password_description),
                style = SplitTheme.typography.body.l,
                color = SplitTheme.colors.neutral.textBody,
                textAlign = TextAlign.Justify
            )
            Spacer(modifier = Modifier.size(24.dp))
            LottieAnimation(
                composition = animation,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier
                    .size(300.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.size(24.dp))
            DSEmailTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = state.email,
                onValueChange = { onEmailChanged(it) },
                placeholder = R.string.enter_your_email
            )
            Spacer(modifier = Modifier.size(24.dp))
            PrimaryLargeButton(
                onClick = { /*TODO*/ },
                text = R.string.send
                //TODO: Add enabled state for the buttons
            )
        }
    }
}

@Preview
@Composable
fun ForgotPasswordScreenPreview() {
    ForgotPasswordScreen(
        navController = rememberNavController()
    )
}