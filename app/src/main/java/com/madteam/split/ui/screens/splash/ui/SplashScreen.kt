package com.madteam.split.ui.screens.splash.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.madteam.split.R
import com.madteam.split.data.model.utils.AuthResult
import com.madteam.split.ui.navigation.Screens
import com.madteam.split.ui.screens.splash.viewmodel.SplashViewModel
import com.madteam.split.ui.theme.SplitTheme
import com.madteam.split.utils.ui.navigateWithPopUpTo

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isAuthenticated) {
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = SplitTheme.colors.neutral.backgroundExtraWeak),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(200.dp),
            painter = painterResource(id = R.drawable.ds_split_logo),
            colorFilter = ColorFilter.tint(SplitTheme.colors.neutral.iconHeavy),
            contentDescription = stringResource(id = R.string.split_logo_description)
        )
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
    SplashScreen(
        navController = rememberNavController()
    )
}