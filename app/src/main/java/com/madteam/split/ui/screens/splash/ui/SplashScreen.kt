package com.madteam.split.ui.screens.splash.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.madteam.split.R
import com.madteam.split.data.model.utils.AuthResult
import com.madteam.split.ui.navigation.Screens
import com.madteam.split.ui.screens.splash.viewmodel.SplashViewModel
import com.madteam.split.ui.theme.SplitTheme
import com.madteam.split.utils.ui.BackPressHandler
import com.madteam.split.utils.ui.navigateWithPopUpTo
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {

    BackPressHandler {
        //Do nothing
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    var nameAnimState by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(state.isAuthenticated, state.isReadyToGo) {
        if (state.isReadyToGo) {
            delay(500)
            nameAnimState = true
            delay(700)
            when (state.isAuthenticated) {
                is AuthResult.Authorized -> {
                    navController.navigateWithPopUpTo(
                        route = Screens.MyGroupsScreen.route,
                        popUpTo = Screens.SplashScreen.route,
                        inclusive = true
                    )
                }

                is AuthResult.Unauthorized -> {
                    navController.navigateWithPopUpTo(
                        route = Screens.WelcomeScreen.route,
                        popUpTo = Screens.SplashScreen.route,
                        inclusive = true
                    )
                }

                is AuthResult.UnknownError -> {
                    println("Unknown error while trying to know if user was authenticated")
                    navController.navigateWithPopUpTo(
                        route = Screens.WelcomeScreen.route,
                        popUpTo = Screens.SplashScreen.route,
                        inclusive = true
                    )
                }

                else -> {
                    //Do nothing
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = SplitTheme.colors.neutral.backgroundExtraWeak),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Image(
                modifier = Modifier
                    .size(200.dp),
                painter = painterResource(id = R.drawable.ds_split_logo),
                colorFilter = ColorFilter.tint(SplitTheme.colors.neutral.iconHeavy),
                contentDescription = stringResource(id = R.string.split_logo_description)
            )
            AnimatedVisibility(
                modifier = Modifier
                    .offset((-30).dp)
                    .align(Alignment.Bottom),
                visible = state.isReadyToGo,
            ) {
                Text(
                    modifier = Modifier,
                    text = "plit",
                    style = SplitTheme.typography.display.l,
                    fontSize = 80.sp,
                    color = SplitTheme.colors.neutral.textTitle
                )
            }
        }
        Spacer(modifier = Modifier.size(24.dp))
        AnimatedVisibility(
            visible = nameAnimState &&
                    state.userInfo != null &&
                    state.userInfo!!.name.isNotBlank(),
        ) {
            Text(
                modifier = Modifier,
                text = "${stringResource(id = R.string.welcome_back)} ${state.userInfo!!.name}",
                style = SplitTheme.typography.body.xl,
                color = SplitTheme.colors.neutral.textTitle
            )
        }
        Spacer(modifier = Modifier.size(96.dp))
        CircularProgressIndicator(
            modifier = Modifier
                .size(50.dp),
            color = SplitTheme.colors.primary.backgroundMedium,
            trackColor = SplitTheme.colors.primary.backgroundWeak
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = SplitTheme.colors.neutral.backgroundExtraWeak),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
        ) {
            Image(
                modifier = Modifier
                    .size(200.dp),
                painter = painterResource(id = R.drawable.ds_split_logo),
                colorFilter = ColorFilter.tint(SplitTheme.colors.neutral.iconHeavy),
                contentDescription = stringResource(id = R.string.split_logo_description)
            )
            AnimatedVisibility(
                modifier = Modifier
                    .offset((-30).dp)
                    .align(Alignment.Bottom),
                visible = true,
            ) {
                Text(
                    modifier = Modifier,
                    text = "plit",
                    style = SplitTheme.typography.display.l,
                    fontSize = 80.sp,
                    color = SplitTheme.colors.neutral.textTitle
                )
            }
        }
        Spacer(modifier = Modifier.size(96.dp))
        CircularProgressIndicator(
            modifier = Modifier
                .size(50.dp),
            color = SplitTheme.colors.primary.backgroundMedium,
            trackColor = SplitTheme.colors.primary.backgroundWeak
        )
    }
}