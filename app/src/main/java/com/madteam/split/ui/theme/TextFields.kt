package com.madteam.split.ui.theme

import androidx.annotation.StringRes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madteam.split.R

@Composable
fun DSTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes placeholder: Int,
    @StringRes supportingText: Int? = null,
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChange(it) },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = SplitTheme.colors.secondary.backgroundMedium,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = SplitTheme.colors.primary.backgroundMedium
        ),
        textStyle = SplitTheme.typography.body.l,
        placeholder = {
            Text(text = stringResource(id = placeholder), style = SplitTheme.typography.body.l, color = SplitTheme.colors.neutral.textMedium)
        },
        singleLine = true,
        shape = RoundedCornerShape(20.dp),
        supportingText = {
            if (supportingText != null) {
                Text(
                    text = stringResource(id = supportingText),
                    style = SplitTheme.typography.body.s,
                    color = SplitTheme.colors.neutral.textStrong
                )
            }
        }
    )
}

@Preview
@Composable
fun DSTextFieldPreview() {
    var text by remember { mutableStateOf("") }
    DSTextField(
        value = text,
        onValueChange = { text = it },
        placeholder = R.string.email_placeholder,
        supportingText = null
    )
}