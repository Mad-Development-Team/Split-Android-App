package com.madteam.split.ui.theme

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madteam.split.R
import com.madteam.split.utils.ui.getEmojiByName

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

@Composable
fun ElevatedIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: ImageVector,
    iconDescription: Int
) {
    IconButton(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = CircleShape,
            )
            .background(
                color = SplitTheme.colors.neutral.backgroundMedium,
                shape = CircleShape
            ),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = SplitTheme.colors.neutral.backgroundMedium
        ),
        onClick = {
            onClick()
        }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = stringResource(id = iconDescription),
            tint = SplitTheme.colors.neutral.iconHeavy
        )
    }
}

@Composable
fun SmallSecondaryButton(
    modifier: Modifier = Modifier,
    emoji: Int? = null,
    buttonText: Int,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    ElevatedButton(
        modifier = modifier,
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 2.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = SplitTheme.colors.neutral.backgroundHeavy,
            disabledContainerColor = SplitTheme.colors.neutral.backgroundMedium,
            contentColor = SplitTheme.colors.neutral.textExtraWeak,
            disabledContentColor = SplitTheme.colors.neutral.textExtraWeak
        ),
        onClick = {
            onClick()
        },
        enabled = enabled
    ) {
        if (emoji != null) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = emoji),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.size(8.dp))
        }
        Text(
            text = stringResource(id = buttonText),
            style = SplitTheme.typography.body.l,
        )
    }
}

@Composable
fun SmallEmojiButton(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .height(56.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = SplitTheme.colors.secondary.backgroundMedium
        ),
        onClick = { onClick() }
    ) {
        Image(
            modifier = Modifier.size(32.dp),
            painter = painterResource(id = image),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}

@Composable
fun BigIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    ElevatedButton(
        modifier = modifier
            .size(100.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 2.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = SplitTheme.colors.secondary.backgroundMedium,
            disabledContainerColor = SplitTheme.colors.neutral.backgroundMedium,
            contentColor = SplitTheme.colors.neutral.textHeavy,
            disabledContentColor = SplitTheme.colors.neutral.textDisabled
        ),
        enabled = enabled,
        onClick = { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = SplitTheme.colors.neutral.iconHeavy
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SmallIconButtonPreview() {
    SmallEmojiButton(
        modifier = Modifier,
        image = R.drawable.emoji_animals_nature_ant, //TODO: Change to emoji euro bill
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun SmallSecondaryButtonPreview() {
    SmallSecondaryButton(
        modifier = Modifier,
        emoji = getEmojiByName("crossmark"),
        buttonText = R.string.discard_changes,
        enabled = true,
        onClick = {}
    )
}

@Preview
@Composable
fun ElevatedIconButtonPreview() {
    ElevatedIconButton(
        modifier = Modifier,
        onClick = {},
        icon = Icons.Filled.Edit,
        iconDescription = R.string.icon_back_description
    )
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