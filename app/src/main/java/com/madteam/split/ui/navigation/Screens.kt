package com.madteam.split.ui.navigation

sealed class Screens(
    val route: String
){
    data object WelcomeScreen : Screens("welcome_screen")
    data object SignInEmailScreen: Screens("sign_in_email_screen")
    data object ForgotPasswordScreen: Screens("forgot_password_screen")
    data object SignUpScreen: Screens("sign_up_screen")
    data object MyGroupsScreen: Screens("my_groups_screen")
    data object MyUserScreen: Screens("my_user_screen")
    data object SplashScreen : Screens("splash_screen")
    data object CreateGroupInfoScreen : Screens("create_group_info_screen")
    data object CreateGroupMembersScreen : Screens("create_group_members_screen")
}
