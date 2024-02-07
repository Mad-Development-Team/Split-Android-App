package com.madteam.split.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.madteam.split.ui.screens.createexpense.ui.CreateExpenseScreen
import com.madteam.split.ui.screens.creategroup.info.ui.CreateGroupInfoScreen
import com.madteam.split.ui.screens.creategroup.invite.ui.CreateGroupInviteScreen
import com.madteam.split.ui.screens.creategroup.members.ui.CreateGroupMembersScreen
import com.madteam.split.ui.screens.forgotpassword.ui.ForgotPasswordScreen
import com.madteam.split.ui.screens.groupbalance.ui.GroupBalanceScreen
import com.madteam.split.ui.screens.groupexpenses.ui.GroupExpensesScreen
import com.madteam.split.ui.screens.grouphome.ui.GroupHomeScreen
import com.madteam.split.ui.screens.groupinfo.ui.GroupInfoScreen
import com.madteam.split.ui.screens.invitecode.ui.InviteCodeScreen
import com.madteam.split.ui.screens.mygroups.ui.MyGroupsScreen
import com.madteam.split.ui.screens.myuser.ui.MyUserScreen
import com.madteam.split.ui.screens.signin.email.ui.SignInEmailScreen
import com.madteam.split.ui.screens.signup.email.ui.SignUpScreen
import com.madteam.split.ui.screens.splash.ui.SplashScreen
import com.madteam.split.ui.screens.welcome.ui.WelcomeScreen

private const val DEFAULT_ANIMATION_DURATION_IN_MILLIS = 500

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.SplashScreen.route
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
                    Screens.WelcomeScreen.route -> {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                        )
                    }

                    Screens.SignUpScreen.route -> {
                        EnterTransition.None
                    }

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Screens.ForgotPasswordScreen.route -> {
                        null
                    }

                    Screens.SignUpScreen.route -> {
                        ExitTransition.None
                    }

                    else -> {
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
            route = Screens.SignUpScreen.route,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Screens.WelcomeScreen.route -> {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                        )
                    }

                    else -> {
                        ExitTransition.None
                    }
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    Screens.WelcomeScreen.route -> {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                        )
                    }

                    else -> {
                        ExitTransition.None
                    }
                }
            }
        ) {
            SignUpScreen(navController = navController)
        }

        composable(
            route = Screens.MyGroupsScreen.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screens.CreateGroupInfoScreen.route,
                    Screens.MyUserScreen.route,
                    -> {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                        )
                    }

                    else -> {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                        )
                    }
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Screens.CreateGroupInfoScreen.route,
                    Screens.MyUserScreen.route,
                    Screens.GroupInfoScreen.route,
                    -> {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                        )
                    }

                    else -> {
                        ExitTransition.None
                    }
                }
            },
            popExitTransition = {
                ExitTransition.None
            }
        ) {
            MyGroupsScreen(navController = navController)
        }

        composable(
            route = Screens.MyUserScreen.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
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
            MyUserScreen(navController = navController)
        }

        composable(
            route = Screens.SplashScreen.route,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                )
            }
        ) {
            SplashScreen(navController = navController)
        }

        composable(
            route = Screens.CreateGroupInfoScreen.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screens.CreateGroupMembersScreen.route -> {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                        )
                    }

                    else -> {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                        )
                    }
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Screens.CreateGroupMembersScreen.route -> {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                        )
                    }

                    else -> {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                        )
                    }
                }
            }
        ) {
            CreateGroupInfoScreen(navController = navController)
        }

        composable(
            route = Screens.CreateGroupMembersScreen.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screens.CreateGroupInviteScreen.route -> {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                        )
                    }

                    else -> {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                        )
                    }
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Screens.CreateGroupInviteScreen.route -> {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                        )
                    }

                    else -> {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                        )
                    }
                }
            }
        ) {
            CreateGroupMembersScreen(navController = navController)
        }

        composable(
            route = Screens.CreateGroupInviteScreen.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                )
            }
        ) {
            CreateGroupInviteScreen(navController = navController)
        }

        composable(
            route = Screens.GroupInfoScreen.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screens.MyGroupsScreen.route -> {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                        )
                    }

                    else -> {
                        EnterTransition.None
                    }
                }
            },
        ) {
            GroupInfoScreen(navController = navController)
        }

        composable(
            route = Screens.GroupHomeScreen.route,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            }
        ) {
            GroupHomeScreen(navController = navController)
        }

        composable(
            route = Screens.GroupExpensesScreen.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screens.CreateExpenseScreen.route -> {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                        )
                    }

                    else -> {
                        EnterTransition.None
                    }
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Screens.CreateExpenseScreen.route -> {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                        )
                    }

                    else -> {
                        ExitTransition.None
                    }
                }
            }
        ) {
            GroupExpensesScreen(navController = navController)
        }

        composable(
            route = Screens.GroupBalanceScreen.route,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            }
        ) {
            GroupBalanceScreen(navController = navController)
        }

        composable(
            route = Screens.InviteCodeScreen.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                )
            }
        ) {
            InviteCodeScreen(navController = navController)
        }

        composable(
            route = Screens.CreateExpenseScreen.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(DEFAULT_ANIMATION_DURATION_IN_MILLIS)
                )
            }
        ) {
            CreateExpenseScreen(navController = navController)
        }
    }
}