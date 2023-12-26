package com.madteam.split.ui.screens.welcome.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.madteam.split.R
import com.madteam.split.ui.screens.welcome.state.WelcomeScreenUIEvent
import com.madteam.split.ui.screens.welcome.state.WelcomeScreenUIState
import com.madteam.split.ui.screens.welcome.viewmodel.WelcomeViewModel
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
            modifier = Modifier.padding(it),
            contentAlignment = Alignment.Center
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
                painter = painterResource(id = R.drawable.ds_split_logo_black),
                contentDescription = stringResource(id = R.string.split_logo_description)
            )
            Text(
                text = "Welcome to Split",
                style = SplitTheme.typography.heading.s,
                color = SplitTheme.colors.neutral.textTitle
            )

        }
        Text(
            text = "Phase: ${state.progressPhase}",
            style = SplitTheme.typography.heading.s,
            color = SplitTheme.colors.neutral.textTitle
        )
        Text(
            text = "Seconds: ${state.progressSeconds} ",
            style = SplitTheme.typography.heading.s,
            color = SplitTheme.colors.neutral.textTitle
        )
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
                animationSpec = tween(durationMillis = PROGRESS_ANIMATION_DURATION_IN_MILLIS, easing = LinearEasing),
                label = "progressIndicatorAnimation"
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