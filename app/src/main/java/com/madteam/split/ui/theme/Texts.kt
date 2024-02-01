package com.madteam.split.ui.theme

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.madteam.split.domain.model.Currency
import com.madteam.split.ui.utils.round
import kotlin.math.absoluteValue

@Composable
fun AmountTextView(
    modifier: Modifier = Modifier,
    amount: Double,
    currency: Currency,
) {
    val amountRounded = amount.round(2)
    val decimalPart = amountRounded.toString().split(".")[1]

    Row(
        modifier = modifier
    ) {
        AnimatedContent(
            targetState = amountRounded.toInt(),
            transitionSpec = {
                if (targetState > initialState) {
                    slideInVertically { -it } togetherWith slideOutVertically { it }
                } else {
                    slideInVertically { it } togetherWith slideOutVertically { -it }
                }
            }, label = ""
        ) { animatedAmountValue ->
            Text(
                modifier = Modifier,
                text = animatedAmountValue.absoluteValue.toString(),
                color = SplitTheme.colors.neutral.textExtraWeak,
                style = SplitTheme.typography.display.s
            )
        }
        AnimatedContent(
            targetState = decimalPart.toInt(),
            transitionSpec = {
                if (targetState > initialState) {
                    slideInVertically { -it } togetherWith slideOutVertically { it }
                } else {
                    slideInVertically { it } togetherWith slideOutVertically { -it }
                }
            }, label = ""
        ) { animatedDecimalValue ->
            Text(
                modifier = Modifier
                    .padding(top = 4.dp),
                text = if (decimalPart == "0" || decimalPart == "00") ""
                else animatedDecimalValue.toString(),
                color = SplitTheme.colors.neutral.textExtraWeak,
                style = SplitTheme.typography.heading.s
            )
        }
        Text(
            modifier = Modifier
                .padding(top = 4.dp),
            text = currency.currencySymbol,
            color = SplitTheme.colors.neutral.textExtraWeak,
            style = SplitTheme.typography.heading.s
        )
    }
}