package com.madteam.split.ui.theme

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madteam.split.R
import com.madteam.split.domain.model.Group

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationAndActionTopAppBar(
    onNavigationClick: () -> Unit,
    onActionClick: () -> Unit,
    @StringRes title: Int,
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

@Composable
fun GroupNavigationTopAppBar(
    currentGroup: Group,
    onExpandClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .background(
                color = SplitTheme.colors.neutral.backgroundExtraWeak
            )
            .fillMaxWidth()
            .statusBarsPadding()
            .height(68.dp)
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = currentGroup.name,
            style = SplitTheme.typography.heading.m,
            color = SplitTheme.colors.neutral.textTitle,
        )
        IconButton(onClick = {
            onExpandClicked()
        }) {
            Icon(
                imageVector = Icons.Outlined.ExpandMore,
                contentDescription = stringResource(
                    id = R.string.icon_show_groups_list_description
                ),
            )
        }
    }
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