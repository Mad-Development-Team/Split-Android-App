package com.madteam.split.ui.theme

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.madteam.split.ui.navigation.BottomNavigationItem

@Composable
fun DSBottomNavigation(navController: NavController) {

    val items = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Expenses,
        BottomNavigationItem.Balance,
        BottomNavigationItem.Group
    )

    NavigationBar(
        modifier = Modifier
            .height(84.dp)
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
            )
            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
        containerColor = SplitTheme.colors.neutral.backgroundExtraWeak,
    ) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = stringResource(id = item.title),
                        modifier = Modifier
                            .height(32.dp)
                            .width(32.dp)
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = item.title),
                        style = SplitTheme.typography.body.m,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = SplitTheme.colors.primary.iconDefault,
                    selectedTextColor = SplitTheme.colors.primary.iconDefault,
                    selectedIndicatorColor = Color.Transparent,
                    unselectedIconColor = SplitTheme.colors.neutral.iconHeavy,
                    unselectedTextColor = SplitTheme.colors.neutral.textTitle,
                    disabledIconColor = Color.Unspecified,
                    disabledTextColor = Color.Unspecified
                )
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 200)
@Composable
fun DSBottomNavigationPreview() {
    DSBottomNavigation(navController = rememberNavController())
}