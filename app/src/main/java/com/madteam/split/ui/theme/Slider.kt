package com.madteam.split.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.RangeSliderState
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmountSlider(
    modifier: Modifier = Modifier,
    minAmount: Double,
    maxAmount: Double,
    onAmountSelected: (Double, Double) -> Unit,
) {
    //TODO: It needs to be fixed, not working
    val sliderState = remember {
        RangeSliderState(
            activeRangeStart = minAmount.toFloat(),
            activeRangeEnd = maxAmount.toFloat(),
            valueRange = minAmount.toFloat()..maxAmount.toFloat(),
        )
    }

    LaunchedEffect(sliderState) {
        onAmountSelected(
            sliderState.valueRange.start.toDouble(), sliderState.valueRange.endInclusive.toDouble()
        )
    }

    RangeSlider(
        state = sliderState,
        modifier = modifier.padding(horizontal = 16.dp),
        colors = SliderDefaults.colors(
            thumbColor = SplitTheme.colors.primary.backgroundMedium,
            activeTrackColor = SplitTheme.colors.primary.backgroundWeak
        )
    )
}

@Preview
@Composable
fun AmountSliderPreview() {
    var minAmountSelected by remember {
        mutableStateOf(0.0)
    }
    var maxAmountSelected by remember {
        mutableStateOf(100.0)
    }

    AmountSlider(
        minAmount = 0.0,
        maxAmount = 100.0,
        onAmountSelected = { minAmount, maxAmount ->
            minAmountSelected = minAmount
            maxAmountSelected = maxAmount
        }
    )
}