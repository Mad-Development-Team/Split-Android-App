package com.madteam.split.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun ThemeShowcase() {
    SplitTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            ColorsShowcase()
        }
    }
}

@Composable
fun ColorsShowcase() {

    val textNeutralColorsList = listOf(
        ThemeShowcaseItem(
            colorTitle = "Title",
            baseColor = "Base/Black",
            textColor = SplitTheme.colors.neutral.textTitle
        ),
        ThemeShowcaseItem(
            colorTitle = "Heavy",
            baseColor = "Grey/900",
            textColor = SplitTheme.colors.neutral.textHeavy
        ),
        ThemeShowcaseItem(
            colorTitle = "Body",
            baseColor = "Grey/900",
            textColor = SplitTheme.colors.neutral.textHeavy
        ),
        ThemeShowcaseItem(
            colorTitle = "Strong",
            baseColor = "Grey/800",
            textColor = SplitTheme.colors.neutral.textStrong
        ),
        ThemeShowcaseItem(
            colorTitle = "Medium",
            baseColor = "Grey/600",
            textColor = SplitTheme.colors.neutral.textMedium
        ),
        ThemeShowcaseItem(
            colorTitle = "Extra Weak",
            baseColor = "Base/White",
            textColor = SplitTheme.colors.neutral.textExtraWeak,
            backgroundColor = SplitTheme.colors.neutral.backgroundHeavy,
            borderColor = SplitTheme.colors.neutral.backgroundHeavy
        ),
        ThemeShowcaseItem(
            colorTitle = "Disabled",
            baseColor = "Grey/500",
            textColor = SplitTheme.colors.neutral.textDisabled
        ),
    )
    val textLinkNeutralColorsList = listOf(
        ThemeShowcaseItem(
            colorTitle = "Default",
            baseColor = "Blue/500",
            textColor = SplitTheme.colors.neutral.textLinkDefault,
            textUnderlined = true
        ),
        ThemeShowcaseItem(
            colorTitle = "Hover",
            baseColor = "Blue/900",
            textColor = SplitTheme.colors.neutral.textLinkHover,
            textUnderlined = true
        ),
        ThemeShowcaseItem(
            colorTitle = "Disabled",
            baseColor = "Grey/400",
            textColor = SplitTheme.colors.neutral.textLinkDisabled,
            textUnderlined = true
        ),
        ThemeShowcaseItem(
            colorTitle = "Extra Weak",
            baseColor = "Base/White",
            textColor = SplitTheme.colors.neutral.textLinkExtraWeak,
            backgroundColor = SplitTheme.colors.neutral.backgroundHeavy,
            borderColor = SplitTheme.colors.neutral.backgroundHeavy,
            textUnderlined = true
        ),
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Text",
            style = SplitTheme.typography.heading.l
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = "Neutral",
            style = SplitTheme.typography.heading.m
        )
        Spacer(modifier = Modifier.padding(16.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(textNeutralColorsList) { _, item ->
                ShowcaseItem(
                    backgroundColor = item.backgroundColor ?: SplitTheme.colors.neutral.backgroundExtraWeak,
                    borderColor = item.borderColor ?: SplitTheme.colors.neutral.borderMedium,
                    textColor = item.textColor ?: SplitTheme.colors.neutral.textTitle,
                    itemText = item.itemText ?: "T",
                    colorTitle = item.colorTitle,
                    baseColor = item.baseColor
                )
            }
        }
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            text = "Error",
            style = SplitTheme.typography.heading.m
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Row {
            ShowcaseItem(
                backgroundColor = SplitTheme.colors.error.backgroundDefault,
                borderColor = SplitTheme.colors.error.backgroundDefault,
                textColor = SplitTheme.colors.error.textDefault,
                colorTitle = "Default",
                baseColor = "Red/700"
            )
        }
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            text = "Success",
            style = SplitTheme.typography.heading.m
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Row {
            ShowcaseItem(
                backgroundColor = SplitTheme.colors.success.backgroundDefault,
                borderColor = SplitTheme.colors.success.backgroundDefault,
                textColor = SplitTheme.colors.success.textDefault,
                colorTitle = "Default",
                baseColor = "Green/900"
            )
        }
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            text = "Text Link",
            style = SplitTheme.typography.heading.l
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = "Neutral",
            style = SplitTheme.typography.heading.m
        )
        Spacer(modifier = Modifier.padding(16.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(textLinkNeutralColorsList) { _, item ->
                ShowcaseItem(
                    backgroundColor = item.backgroundColor ?: SplitTheme.colors.neutral.backgroundExtraWeak,
                    borderColor = item.borderColor ?: SplitTheme.colors.neutral.borderMedium,
                    textColor = item.textColor ?: SplitTheme.colors.neutral.textTitle,
                    textUnderlined = item.textUnderlined,
                    itemText = item.itemText ?: "T",
                    colorTitle = item.colorTitle,
                    baseColor = item.baseColor
                )
            }
        }
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            text = "Icon",
            style = SplitTheme.typography.heading.l
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = "Neutral",
            style = SplitTheme.typography.heading.m
        )
        Spacer(modifier = Modifier.padding(16.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ShowcaseItem(
                    backgroundColor = SplitTheme.colors.neutral.backgroundExtraWeak,
                    borderColor = SplitTheme.colors.neutral.borderMedium,
                    iconColor = SplitTheme.colors.neutral.iconHeavy,
                    colorTitle = "Heavy",
                    baseColor = "Base/Black"
                )
            }
            item {
                ShowcaseItem(
                    backgroundColor = SplitTheme.colors.neutral.backgroundHeavy,
                    borderColor = SplitTheme.colors.neutral.borderStrong,
                    iconColor = SplitTheme.colors.neutral.iconExtraWeak,
                    colorTitle = "Extra Weak",
                    baseColor = "Base/White"
                )
            }
        }
    }
}

@Composable
fun ShowcaseItem(
    backgroundColor: Color = SplitTheme.colors.neutral.backgroundExtraWeak,
    borderColor: Color = SplitTheme.colors.neutral.borderMedium,
    textColor: Color = SplitTheme.colors.neutral.textTitle,
    iconColor: Color? = null,
    textUnderlined: Boolean = false,
    itemText: String = "T",
    colorTitle: String = "Undefined",
    baseColor: String = "Undefined"
) {
    Column {
        Box(
            modifier = Modifier
                .size(144.dp)
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(20.dp)
                )
                .border(
                    width = 2.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(20.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (iconColor != null) {
                Icon(
                    modifier = Modifier.size(96.dp),
                    imageVector = Icons.Filled.Bookmark,
                    tint = iconColor,
                    contentDescription = "Bookmark icon"
                )
            } else {
                Text(
                    text = itemText,
                    style = SplitTheme.typography.display.l,
                    color = textColor,
                    textDecoration = if (textUnderlined) TextDecoration.Underline else TextDecoration.None
                )
            }
        }
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            text = colorTitle,
            style = SplitTheme.typography.heading.xxs,
            color = SplitTheme.colors.neutral.textHeavy
        )
        Text(
            text = baseColor,
            style = SplitTheme.typography.body.m,
            color = SplitTheme.colors.neutral.textMedium
        )
    }
}

data class ThemeShowcaseItem(
    val backgroundColor: Color? = null,
    val borderColor: Color? = null,
    val textColor: Color? = null,
    val textUnderlined: Boolean = false,
    val itemText: String? = null,
    val colorTitle: String,
    val baseColor: String
)