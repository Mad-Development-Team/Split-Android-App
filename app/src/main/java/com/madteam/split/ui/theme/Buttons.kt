package com.madteam.split.ui.theme

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madteam.split.R

@Composable
fun SecondaryLargeButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    @StringRes text: Int,
    @DrawableRes icon: Int? = null,
    @StringRes iconDescription: Int? = null,
    iconTint: Color? = null
) {
    BaseLargeButton(
        onClick = { onClick() },
        text = text,
        containerColor = SplitTheme.colors.neutral.backgroundHeavy,
        disabledContainerColor = SplitTheme.colors.neutral.backgroundMedium,
        textColor = SplitTheme.colors.neutral.textExtraWeak,
        enabled = enabled,
        iconTint = iconTint,
        modifier = modifier,
        icon = icon,
        iconDescription = iconDescription
    )
}

@Composable
fun PrimaryLargeButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    @StringRes text: Int,
    @DrawableRes icon: Int? = null,
    @StringRes iconDescription: Int? = null,
    iconTint: Color? = null
) {
    BaseLargeButton(
        onClick = { onClick() },
        text = text,
        containerColor = SplitTheme.colors.primary.backgroundStrong,
        disabledContainerColor = SplitTheme.colors.neutral.backgroundMedium,
        textColor = SplitTheme.colors.neutral.textExtraWeak,
        enabled = enabled,
        iconTint = iconTint,
        modifier = modifier,
        icon = icon,
        iconDescription = iconDescription
    )
}

@Composable
fun DangerLargeButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    @StringRes text: Int,
    @DrawableRes icon: Int? = null,
    @StringRes iconDescription: Int? = null,
    iconTint: Color? = null
) {
    BaseLargeButton(
        onClick = { onClick() },
        text = text,
        containerColor = SplitTheme.colors.error.backgroundDefault,
        disabledContainerColor = SplitTheme.colors.neutral.backgroundMedium,
        textColor = SplitTheme.colors.error.textDefault,
        enabled = enabled,
        iconTint = iconTint,
        modifier = modifier,
        icon = icon,
        iconDescription = iconDescription
    )
}

@Composable
internal fun BaseLargeButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    @StringRes text: Int,
    @DrawableRes icon: Int? = null,
    @StringRes iconDescription: Int? = null,
    iconTint: Color? = null,
    containerColor: Color,
    disabledContainerColor: Color,
    textColor: Color
) {
    ElevatedButton(
        modifier = modifier
            .fillMaxWidth(),
        enabled = enabled,
        shape = RoundedCornerShape(40.dp),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = containerColor,
            disabledContainerColor = disabledContainerColor,
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 2.dp,
            disabledElevation = 0.dp
        ),
        onClick = { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null && iconDescription != null && iconTint != null) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = stringResource(id = iconDescription),
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
            }
            Text(
                text = stringResource(id = text),
                color = textColor,
                style = SplitTheme.typography.body.xxl,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
fun SecondaryLargeButtonPreview() {
    SecondaryLargeButton(
        onClick = {},
        text = R.string.continue_with_google,
        icon = R.drawable.ds_ic_google_solid,
        iconTint = SplitTheme.colors.neutral.iconExtraWeak,
        iconDescription = R.string.google_icon_description,
        enabled = false
    )
}

@Preview(showBackground = true)
@Composable
fun PrimaryLargeButtonPreview() {
    PrimaryLargeButton(
        onClick = {},
        text = R.string.continue_with_google,
        icon = R.drawable.ds_ic_google_solid,
        iconTint = SplitTheme.colors.neutral.iconExtraWeak,
        iconDescription = R.string.google_icon_description,
        enabled = false
    )
}

@Preview(showBackground = true)
@Composable
fun DangerLargeButtonPreview() {
    DangerLargeButton(
        onClick = {},
        text = R.string.continue_with_google,
        enabled = true
    )
}