package com.madteam.split.ui.screens.groupexpenses.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.madteam.split.R
import com.madteam.split.ui.theme.SplitTheme
import kotlin.math.absoluteValue

@Composable
fun GroupExpensesSummarySection(
    modifier: Modifier = Modifier,
    values: Map<Int, Double>,
    currency: String,
) {

    var index by remember { mutableStateOf(1) }
    val amountValue = values.values.elementAt(index)
    val indexOfDecimal = amountValue.toString().indexOf('.')
    val decimalPart = amountValue.toString().substring(indexOfDecimal + 1)

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConstraintLayout(
            modifier = modifier
        ) {
            val (blob, amount, chevronLeft, chevronRight) = createRefs()
            Image(
                modifier = Modifier
                    .constrainAs(blob) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .size(150.dp),
                painter = painterResource(id = R.drawable.blob),
                contentDescription = null
            )
            Row(
                modifier = Modifier
                    .constrainAs(amount) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                AnimatedContent(
                    targetState = amountValue.toInt(),
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
                    text = currency,
                    color = SplitTheme.colors.neutral.textExtraWeak,
                    style = SplitTheme.typography.heading.s
                )
            }
            IconButton(
                modifier = Modifier
                    .constrainAs(chevronLeft) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(blob.start, 16.dp)
                    },
                onClick = {
                    if (index > 0) {
                        index--
                    } else {
                        index = values.size - 1
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.ChevronLeft,
                    contentDescription = null
                )
            }
            IconButton(
                modifier = Modifier
                    .constrainAs(chevronRight) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(blob.end, 16.dp)
                    },
                onClick = {
                    if (index < 2) {
                        index++
                    } else {
                        index = 0
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.ChevronRight,
                    contentDescription = null
                )
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = stringResource(id = values.keys.elementAt(index)),
            style = SplitTheme.typography.heading.s,
            color = SplitTheme.colors.neutral.textTitle
        )
    }
}