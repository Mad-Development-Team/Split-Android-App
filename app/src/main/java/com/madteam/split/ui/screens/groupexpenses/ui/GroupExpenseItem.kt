package com.madteam.split.ui.screens.groupexpenses.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.madteam.split.ui.theme.SplitTheme

@Composable
fun GroupExpenseItem() {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        colors = CardColors(
            containerColor = SplitTheme.colors.primary.backgroundWeak,
            contentColor = SplitTheme.colors.neutral.textStrong,
            disabledContentColor = SplitTheme.colors.neutral.textDisabled,
            disabledContainerColor = SplitTheme.colors.neutral.backgroundMedium,
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 0.dp,
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            val (title, description, date, amount, payer, pays, type) = createRefs()
            //TODO: Finish design
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GroupExpenseItemPreview() {
    GroupExpenseItem()
}