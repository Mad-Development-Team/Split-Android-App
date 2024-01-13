package com.madteam.split.ui.theme

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.madteam.split.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationAndActionTopAppBar(
    onNavigationClick: () -> Unit,
    onActionClick: () -> Unit,
    @StringRes title: Int
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = SplitTheme.colors.neutral.backgroundExtraWeak,
            navigationIconContentColor = SplitTheme.colors.neutral.iconHeavy,
            actionIconContentColor = SplitTheme.colors.neutral.iconHeavy
        ),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = title),
                    style = SplitTheme.typography.heading.m,
                    color = SplitTheme.colors.neutral.textTitle,
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = { onNavigationClick() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = SplitTheme.colors.neutral.iconHeavy,
                    contentDescription = stringResource(id = R.string.icon_back_description)
                )
            }
        },
        actions = {
            IconButton(
                onClick = { onActionClick() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    tint = SplitTheme.colors.neutral.iconHeavy,
                    contentDescription = stringResource(id = R.string.icon_back_description)
                )
            }
        }
    )

}

@Preview
@Composable
fun NavigationAndActionTopAppBarPreview() {
    NavigationAndActionTopAppBar(
        onNavigationClick = {},
        onActionClick = {},
        title = R.string.app_name
    )
}