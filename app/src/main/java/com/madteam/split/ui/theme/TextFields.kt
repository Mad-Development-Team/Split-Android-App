package com.madteam.split.ui.theme

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madteam.split.R

@Composable
private fun DSTextField(
    modifier: Modifier = Modifier,
    value: String,
    enabled: Boolean,
    keyboardOptions: KeyboardOptions,
    visualTransformation: VisualTransformation,
    maxLines: Int,
    isError: Boolean,
    onIconsClick: () -> Unit = {},
    customTrailingIcon: ImageVector? = null,
    isSuccess: Boolean,
    readOnly: Boolean = false,
    onValueChange: (String) -> Unit,
    @StringRes placeholder: Int,
    @StringRes supportingText: Int? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    val focusManager = LocalFocusManager.current
    TextField(
        modifier = modifier
            .fillMaxWidth(),
        value = value,
        enabled = enabled,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        maxLines = maxLines,
        visualTransformation = visualTransformation,
        leadingIcon = leadingIcon,
        onValueChange = { onValueChange(it) },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = SplitTheme.colors.secondary.backgroundMedium,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = SplitTheme.colors.primary.backgroundMedium,
            errorCursorColor = SplitTheme.colors.error.iconDefault,
            errorIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorSupportingTextColor = SplitTheme.colors.error.textDefault,
            errorTrailingIconColor = SplitTheme.colors.error.iconDefault,
            focusedSupportingTextColor = SplitTheme.colors.neutral.textStrong,
            disabledSupportingTextColor = SplitTheme.colors.neutral.textDisabled,
            disabledLabelColor = SplitTheme.colors.neutral.textDisabled,
        ),
        readOnly = readOnly,
        isError = isError,
        trailingIcon = {
            when {
                customTrailingIcon != null -> {
                    IconButton(onClick = { onIconsClick() }) {
                        Icon(
                            imageVector = customTrailingIcon,
                            contentDescription = null
                        )
                    }
                }

                isError -> {
                    Icon(
                        imageVector = Icons.Filled.Error,
                        contentDescription = stringResource(id = R.string.error_icon_description)
                    )
                }

                isSuccess -> {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        tint = SplitTheme.colors.success.iconDefault,
                        contentDescription = stringResource(id = R.string.success_icon_description)
                    )
                }
            }
        },
        textStyle = SplitTheme.typography.body.l,
        placeholder = {
            Text(
                text = stringResource(id = placeholder),
                style = SplitTheme.typography.body.l,
                color = SplitTheme.colors.neutral.textMedium
            )
        },
        shape = RoundedCornerShape(20.dp),
        supportingText = {
            AnimatedVisibility(
                visible = supportingText != null,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> -fullHeight },
                    animationSpec = tween(durationMillis = 300)
                ),
            ) {
                if (supportingText != null) {
                    Text(
                        text = stringResource(id = supportingText),
                        style = SplitTheme.typography.body.s
                    )
                }
            }
        }
    )
}

@Composable
fun DSEmailTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    isError: Boolean = false,
    isSuccess: Boolean = false,
    imeAction: ImeAction = ImeAction.Next,
    @StringRes placeholder: Int,
    @StringRes supportingText: Int? = null,
) {
    DSTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = placeholder,
        supportingText = supportingText,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = imeAction
        ),
        maxLines = 1,
        isError = isError,
        isSuccess = isSuccess,
        visualTransformation = VisualTransformation.None
    )
}

@Composable
fun DSBasicTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    isError: Boolean = false,
    isSuccess: Boolean = false,
    imeAction: ImeAction = ImeAction.Next,
    maxLines: Int = 1,
    @StringRes placeholder: Int,
    @StringRes supportingText: Int? = null,
) {
    DSTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = placeholder,
        supportingText = supportingText,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = imeAction
        ),
        maxLines = maxLines,
        isError = isError,
        isSuccess = isSuccess,
        visualTransformation = VisualTransformation.None
    )
}

@Composable
fun DSPasswordTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    isError: Boolean = false,
    isSuccess: Boolean = false,
    imeAction: ImeAction = ImeAction.Next,
    @StringRes placeholder: Int,
    @StringRes supportingText: Int? = null,
) {
    DSTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = placeholder,
        supportingText = supportingText,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        maxLines = 1,
        isError = isError,
        isSuccess = isSuccess,
        visualTransformation = PasswordVisualTransformation()
    )
}

@Composable
fun DSDatePickerTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onCalendarClick: () -> Unit,
    enabled: Boolean = true,
    isError: Boolean = false,
    isSuccess: Boolean = false,
    imeAction: ImeAction = ImeAction.Next,
    @StringRes placeholder: Int,
    @StringRes supportingText: Int? = null,
) {
    DSTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChange(it) },
        leadingIcon = {
            IconButton(onClick = { onCalendarClick() }) {
                Icon(
                    imageVector = Icons.Filled.CalendarMonth,
                    contentDescription = stringResource(id = R.string.calendar_icon_description)
                )
            }
        },
        customTrailingIcon = Icons.Filled.ExpandMore,
        onIconsClick = { onCalendarClick() },
        placeholder = placeholder,
        supportingText = supportingText,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        maxLines = 1,
        readOnly = true,
        isError = isError,
        isSuccess = isSuccess,
        visualTransformation = VisualTransformation.None
    )
}

@Composable
fun DSCurrencyTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    isError: Boolean = false,
    isSuccess: Boolean = false,
    imeAction: ImeAction = ImeAction.Next,
    @StringRes placeholder: Int,
    @StringRes supportingText: Int? = null,
) {
    DSTextField(
        modifier = modifier,
        value = value,
        onValueChange = { newValue ->
            val isValid = when {
                newValue.isEmpty() -> true
                newValue.length == 1 && newValue.all { it.isDigit() } -> true
                newValue.count { it == ',' || it == '.' } <= 1 -> {
                    val parts =
                        if (newValue.contains(',')) newValue.split(',') else newValue.split('.')
                    when {
                        parts.size > 2 -> false
                        parts[0].length <= 5 && (parts.getOrNull(1)?.length ?: 0) <= 2 -> true
                        else -> false
                    }
                }

                else -> false
            }
            if (isValid) {
                onValueChange(newValue)
            }
        },
        placeholder = placeholder,
        supportingText = supportingText,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        ),
        maxLines = 1,
        isError = isError,
        isSuccess = isSuccess,
        visualTransformation = VisualTransformation.None
    )
}

@Preview
@Composable
fun DSTextFieldPreview() {
    var text by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    isError = text.length > 5
    DSEmailTextField(
        value = text,
        onValueChange = { text = it },
        placeholder = R.string.enter_your_email,
        isError = isError,
        supportingText = R.string.enter_your_email,
        isSuccess = !isError
    )
}