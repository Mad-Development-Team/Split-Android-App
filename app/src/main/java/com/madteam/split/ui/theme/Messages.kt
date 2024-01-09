package com.madteam.split.ui.theme

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madteam.split.R
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
private fun DSBaseMessage(
    modifier: Modifier = Modifier,
    leadingIconTint: Color,
    closeIconTint: Color,
    backgroundColor: Color,
    borderColor: Color,
    textColor: Color,
    cancelable: Boolean = false,
    onCloseClick: () -> Unit = {},
    onLinkClick: (String) -> Unit = {},
    titleText: Int? = null,
    messageText: Int,
    leadingIcon: ImageVector,
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
            ) {
                Icon(
                    imageVector = leadingIcon,
                    tint = leadingIconTint,
                    contentDescription = null
                )
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .weight(0.8f)
            ) {
                if (titleText != null) {
                    Text(
                        text = stringResource(id = titleText),
                        style = SplitTheme.typography.body.m,
                        color = textColor,
                        textAlign = TextAlign.Start,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                MarkdownText(
                    modifier = Modifier.fillMaxWidth(),
                    markdown = stringResource(id = messageText),
                    style = SplitTheme.typography.body.m,
                    fontResource = R.font.poppins_regular,
                    color = textColor,
                    linkColor = textColor,
                    onLinkClicked = { link ->
                        onLinkClick(link)
                    }
                )
            }
            if (cancelable) {
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Icon(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(RoundedCornerShape(100))
                            .clickable { onCloseClick() },
                        imageVector = Icons.Filled.Close,
                        tint = closeIconTint,
                        contentDescription = stringResource(id = R.string.close_icon_description)
                    )
                }
            }
        }

    }
}

@Composable
fun InfoMessage(
    modifier: Modifier = Modifier,
    @StringRes messageText: Int,
    @StringRes titleText: Int? = null,
    cancelable: Boolean = false,
    onCloseClick: () -> Unit = {},
    onLinkClick: (String) -> Unit = {}
) {
    DSBaseMessage(
        modifier = modifier,
        backgroundColor = SplitTheme.colors.primary.backgroundWeak,
        borderColor = SplitTheme.colors.primary.backgroundMedium,
        textColor = SplitTheme.colors.primary.backgroundMedium,
        closeIconTint = SplitTheme.colors.primary.backgroundMedium,
        leadingIconTint = SplitTheme.colors.primary.backgroundMedium,
        leadingIcon = Icons.Filled.Info,
        messageText = messageText,
        titleText = titleText,
        cancelable = cancelable,
        onCloseClick = { onCloseClick() },
        onLinkClick = { onLinkClick(it) }
    )
}

@Composable
fun ErrorMessage(
    modifier: Modifier = Modifier,
    messageText: Int,
    titleText: Int? = null,
    cancelable: Boolean = false,
    onCloseClick: () -> Unit = {},
    onLinkClick: (String) -> Unit = {}
) {
    DSBaseMessage(
        modifier = modifier,
        backgroundColor = SplitTheme.colors.error.backgroundDefault,
        borderColor = SplitTheme.colors.error.borderDefault,
        textColor = SplitTheme.colors.error.textDefault,
        closeIconTint = SplitTheme.colors.error.iconDefault,
        leadingIconTint = SplitTheme.colors.error.iconDefault,
        leadingIcon = Icons.Filled.Warning,
        messageText = messageText,
        titleText = titleText,
        cancelable = cancelable,
        onCloseClick = { onCloseClick() },
        onLinkClick = { onLinkClick(it) }
    )
}

@Composable
fun SuccessMessage(
    modifier: Modifier = Modifier,
    messageText: Int,
    titleText: Int? = null,
    cancelable: Boolean = false,
    onCloseClick: () -> Unit = {},
    onLinkClick: (String) -> Unit = {}
) {
    DSBaseMessage(
        modifier = modifier,
        backgroundColor = SplitTheme.colors.success.backgroundDefault,
        borderColor = SplitTheme.colors.success.borderDefault,
        textColor = SplitTheme.colors.success.textDefault,
        closeIconTint = SplitTheme.colors.success.iconDefault,
        leadingIconTint = SplitTheme.colors.success.iconDefault,
        leadingIcon = Icons.Filled.CheckCircle,
        messageText = messageText,
        titleText = titleText,
        cancelable = cancelable,
        onCloseClick = { onCloseClick() },
        onLinkClick = { onLinkClick(it) }
    )
}

@Preview
@Composable
fun InfoMessagePreview() {
    InfoMessage(
        messageText = R.string.shared_user_changes_info,
        cancelable = true
    )
}