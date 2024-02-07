package com.madteam.split.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DefaultFloatingButton(
    icon: ImageVector,
    contentDescription: String? = null,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = SplitTheme.colors.primary.backgroundMedium
    ) {
        Icon(
            imageVector = icon,
            tint = SplitTheme.colors.primary.backgroundWeak,
            contentDescription = contentDescription
        )
    }
}

@Preview
@Composable
fun DefaultFloatingButtonPreview() {
    DefaultFloatingButton(
        icon = Icons.Outlined.Add,
        onClick = {}
    )
}