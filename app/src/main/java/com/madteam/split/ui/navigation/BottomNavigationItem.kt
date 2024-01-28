package com.madteam.split.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Payments
import androidx.compose.ui.graphics.vector.ImageVector
import com.madteam.split.R

sealed class BottomNavigationItem(
    var title: Int,
    var icon: ImageVector,
    var route: String,
) {

    data object Home : BottomNavigationItem(
        R.string.menu_tab_home,
        Icons.Filled.Home,
        Screens.GroupHomeScreen.route
    )

    data object Expenses : BottomNavigationItem(
        R.string.menu_tab_expenses,
        Icons.Filled.Payments,
        Screens.GroupExpensesScreen.route
    )

    data object Balance : BottomNavigationItem(
        R.string.menu_tab_balance,
        Icons.Filled.AccountBalance,
        Screens.GroupBalanceScreen.route
    )

    data object Group : BottomNavigationItem(
        R.string.menu_tab_group,
        Icons.Filled.Groups,
        Screens.GroupInfoScreen.route
    )
}
