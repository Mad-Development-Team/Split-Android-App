package com.madteam.split.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.madteam.split.ui.screens.forgotpassword.ui.ForgotPasswordScreen
import com.madteam.split.ui.screens.signin.email.ui.SignInEmailScreen
import com.madteam.split.ui.screens.signup.email.ui.SignUpScreen
import com.madteam.split.ui.screens.welcome.ui.WelcomeScreen

private const val DEFAULT_ANIMATION_DURATION_IN_MILLIS = 500

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.WelcomeScreen.route
    ) {

        composable(
            route = Screens.WelcomeScreen.route,
            exitTransition = {
                when (targetState.destination.route) {
                    Screens.SignInEmailScreen.route ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                        )

                    else -> null
                }
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                )
            }
        ) {
            WelcomeScreen(navController = navController)
        }

        composable(
            route = Screens.SignInEmailScreen.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screens.WelcomeScreen.route ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                        )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Screens.ForgotPasswordScreen.route -> {
                        null
                    } else -> {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                        )
                    }
                }
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                )
            }
        ) {
            SignInEmailScreen(navController = navController)
        }

        composable(
            route = Screens.ForgotPasswordScreen.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                )
            }
        ) {
            ForgotPasswordScreen(navController = navController)
        }

        composable(
            route = Screens.SignUpScreen.route
        ) {
            SignUpScreen(navController = navController)
        }
    }
}