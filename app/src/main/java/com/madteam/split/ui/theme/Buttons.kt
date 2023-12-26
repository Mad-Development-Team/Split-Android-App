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
    @StringRes text: Int,
    @DrawableRes icon: Int,
    @StringRes iconDescription: Int,
    iconTint: Color
) {
    ElevatedButton(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(40.dp),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = SplitTheme.colors.neutral.backgroundHeavy
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 2.dp
        ),
        onClick = { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = stringResource(id = iconDescription),
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = stringResource(id = text),
                color = SplitTheme.colors.neutral.textExtraWeak,
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
        iconDescription = R.string.google_icon_description
    )
}