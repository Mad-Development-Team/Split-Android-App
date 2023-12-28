package com.madteam.split.ui.theme

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview

private const val LINK_START_CHAR = '['
private const val LINK_END_CHAR = ']'

@Composable
fun DSCheckBoxTextWithLink(
    modifier: Modifier = Modifier,
    checked: Boolean,
    enabled: Boolean = true,
    onCheckedChange: () -> Unit,
    @StringRes text: Int,
    link: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val uriHandler = LocalUriHandler.current
            val linkStyle = SpanStyle(
                color = SplitTheme.colors.neutral.textLinkDefault,
                textDecoration = TextDecoration.Underline
            )
            val textStyle = SpanStyle(
                color = SplitTheme.colors.neutral.textBody
            )
            val string = stringResource(id = text)
            val annotatedString = buildAnnotatedString {
                pushStringAnnotation(tag = "link", annotation = link)
                withStyle(style = textStyle) {
                    append(string)
                }
                addStyle(
                    style = linkStyle,
                    start = string.indexOf(LINK_START_CHAR),
                    end = string.lastIndexOf(LINK_END_CHAR) + 1
                )
            }
            Checkbox(
                checked = checked,
                enabled = enabled,
                onCheckedChange = { onCheckedChange() },
                colors = CheckboxDefaults.colors(
                    checkedColor = SplitTheme.colors.primary.backgroundStrong
                )
            )
            ClickableText(
                text = annotatedString,
                style = SplitTheme.typography.body.l,
                onClick = { offset ->
                    annotatedString.getStringAnnotations(tag = "link", start = offset, end = offset).firstOrNull()
                        ?.let {
                            uriHandler.openUri(it.item)
                        }
                }
            )
        }
    }

}

@Preview
@Composable
fun DSCheckBoxTextWithLinkPreview() {

}