package com.madteam.split.ui.theme

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madteam.split.R
import dev.jeziellago.compose.markdowntext.MarkdownText

private const val LINK_START_CHAR = '['
private const val LINK_END_CHAR = ']'

@Composable
fun DSCheckBoxTextWithLink(
    modifier: Modifier = Modifier,
    checked: Boolean,
    enabled: Boolean = true,
    isError: Boolean = false,
    onCheckedChange: () -> Unit,
    @StringRes text: Int,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(
                color = if (isError) {
                    SplitTheme.colors.error.backgroundDefault
                } else {
                    Color.Transparent
                }
            )
            .border(
                width = if (isError) {
                    1.dp
                } else {
                    0.dp
                },
                color = if (isError) {
                    SplitTheme.colors.error.borderDefault
                } else {
                    Color.Transparent
                },
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val uriHandler = LocalUriHandler.current
            Checkbox(
                checked = checked,
                enabled = enabled,
                onCheckedChange = { onCheckedChange() },
                colors = CheckboxDefaults.colors(
                    checkedColor = SplitTheme.colors.primary.backgroundStrong,
                    uncheckedColor = if (isError) {
                        SplitTheme.colors.error.borderDefault
                    } else {
                        SplitTheme.colors.neutral.borderStrong
                    },
                )
            )
            MarkdownText(
                markdown = stringResource(id = text),
                style = SplitTheme.typography.body.l,
                fontResource = R.font.poppins_regular,
                linkColor = SplitTheme.colors.neutral.textLinkDefault,
                onLinkClicked = { link ->
                    uriHandler.openUri(link)
                },
                color = if (isError) {
                    SplitTheme.colors.error.textDefault
                } else {
                    SplitTheme.colors.neutral.textBody
                }
            )
        }
    }
}

@Preview
@Composable
fun DSCheckBoxTextWithLinkPreview() {
    DSCheckBoxTextWithLink(
        checked = false,
        isError = true,
        onCheckedChange = {},
        text = R.string.accept_terms_and_policy,
    )
}