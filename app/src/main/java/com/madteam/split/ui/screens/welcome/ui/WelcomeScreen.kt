package com.madteam.split.ui.screens.welcome.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.madteam.split.R
import com.madteam.split.ui.screens.welcome.state.WelcomeScreenUIEvent
import com.madteam.split.ui.screens.welcome.state.WelcomeScreenUIState
import com.madteam.split.ui.screens.welcome.viewmodel.WelcomeViewModel
import com.madteam.split.ui.theme.SecondaryLargeButton
import com.madteam.split.ui.theme.SplitTheme

private const val PROGRESS_ANIMATION_DURATION_IN_MILLIS = 1000

@Composable
fun WelcomeScreen(
    viewModel: WelcomeViewModel = hiltViewModel()
) {
    val welcomeScreenUIState by viewModel.welcomeScreenUIState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onEvent(WelcomeScreenUIEvent.OnStart)
    }

    Scaffold(
        containerColor = SplitTheme.colors.neutral.backgroundExtraWeak
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            WelcomeContent(
                state = welcomeScreenUIState
            )
        }
    }
}

@Composable
fun WelcomeContent(
    state: WelcomeScreenUIState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 24.dp
            )
    ) {
        Spacer(modifier = Modifier.size(8.dp))
        WelcomeScreenProgressIndicator(
            totalPhases = state.totalPhases,
            currentPhase = state.progressPhase,
            phaseSeconds = state.progressSeconds
        )
        Spacer(modifier = Modifier.size(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(48.dp),
                painter = painterResource(id = R.drawable.ds_split_logo),
                colorFilter = ColorFilter.tint(SplitTheme.colors.neutral.iconHeavy),
                contentDescription = stringResource(id = R.string.split_logo_description)
            )
            Text(
                text = "Welcome to Split",
                style = SplitTheme.typography.heading.s,
                color = SplitTheme.colors.neutral.textTitle
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        WelcomeScreenCaption(
            currentPhase = state.progressPhase
        )
        Spacer(modifier = Modifier.size(68.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            WelcomeScreenAnimation(
                currentPhase = state.progressPhase
            )
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 24.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SecondaryLargeButton(
                onClick = {},
                text = R.string.continue_with_google,
                icon = R.drawable.ds_ic_google_solid,
                iconTint = SplitTheme.colors.neutral.iconExtraWeak,
                iconDescription = R.string.google_icon_description
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = "Or continue with email",
                style = SplitTheme.typography.textLink.l,
                color = SplitTheme.colors.neutral.textLinkDefault
            )
        }
    }
}

@Composable
fun WelcomeScreenProgressIndicator(
    totalPhases: Int,
    currentPhase: Int,
    phaseSeconds: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        repeat(totalPhases) { phase ->
            val progressAnimation by animateFloatAsState(
                targetValue = calculateProgress(
                    phase = phase,
                    currentPhase = currentPhase,
                    phaseSeconds = phaseSeconds
                ),
                animationSpec = if (currentPhase == 0 && phaseSeconds == 0) {
                    snap()
                } else {
                    tween(durationMillis = 1000, easing = LinearEasing)
                },
                label = "progressIndicatorAnimation_$phase"
            )
            LinearProgressIndicator(
                modifier = Modifier
                    .weight(1f / totalPhases)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp)),
                trackColor = SplitTheme.colors.neutral.backgroundMedium,
                color = SplitTheme.colors.primary.backgroundStrong,
                progress = { progressAnimation }
            )
        }
    }
}

@Composable
fun WelcomeScreenCaption(
    currentPhase: Int
) {
    val firstCaption = when (currentPhase) {
        0 -> stringResource(id = R.string.welcome_screen_first_caption_first_phase)
        1 -> stringResource(id = R.string.welcome_screen_first_caption_second_phase)
        2 -> stringResource(id = R.string.welcome_screen_first_caption_third_phase)
        3 -> stringResource(id = R.string.welcome_screen_first_caption_fourth_phase)
        else -> ""
    }
    val secondCaption = when (currentPhase) {
        0 -> stringResource(id = R.string.welcome_screen_second_caption_first_phase)
        1 -> stringResource(id = R.string.welcome_screen_second_caption_second_phase)
        2 -> stringResource(id = R.string.welcome_screen_second_caption_third_phase)
        3 -> stringResource(id = R.string.welcome_screen_second_caption_fourth_phase)
        else -> ""
    }
    val thirdCaption = when (currentPhase) {
        0 -> stringResource(id = R.string.welcome_screen_third_caption_first_phase)
        1 -> stringResource(id = R.string.welcome_screen_third_caption_second_phase)
        2 -> stringResource(id = R.string.welcome_screen_third_caption_third_phase)
        3 -> stringResource(id = R.string.welcome_screen_third_caption_fourth_phase)
        else -> ""
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = firstCaption,
            style = SplitTheme.typography.heading.l,
            color = SplitTheme.colors.neutral.textTitle
        )
        Text(
            text = secondCaption,
            style = SplitTheme.typography.heading.l,
            color = SplitTheme.colors.neutral.textTitle
        )
        Text(
            text = thirdCaption,
            style = SplitTheme.typography.heading.l,
            color = SplitTheme.colors.neutral.textTitle
        )
    }
}

@Composable
fun WelcomeScreenAnimation(
    currentPhase: Int
) {
    val animation by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            when (currentPhase) {
                0 -> R.raw.welcome_screen_animation_first_phase
                1 -> R.raw.welcome_screen_animation_second_phase
                2 -> R.raw.welcome_screen_animation_third_phase
                3 -> R.raw.welcome_screen_animation_fourth_phase
                else -> R.raw.welcome_screen_animation_first_phase
            }
        )
    )
    LottieAnimation(
        composition = animation,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier
            .size(300.dp)
    )
}

private fun calculateProgress(phase: Int, currentPhase: Int, phaseSeconds: Int): Float {
    return when {
        phase < currentPhase -> 1f
        phase == currentPhase -> phaseSeconds / 5f
        else -> 0f
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen()
}