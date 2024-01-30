package com.madteam.split.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class ExpenseFilter(
    val title: Int,
    val icon: ImageVector? = null,
    val enabled: Boolean = true,
    val selected: Boolean = false,
    val onClick: () -> Unit,
)
