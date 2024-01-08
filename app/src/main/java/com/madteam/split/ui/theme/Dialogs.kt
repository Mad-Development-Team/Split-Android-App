package com.madteam.split.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.madteam.split.R

@Composable
fun LoadingDialog() {
    ElevatedCard(
        modifier = Modifier
            .size(150.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = SplitTheme.colors.neutral.backgroundExtraWeak
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp),
                color = SplitTheme.colors.primary.backgroundMedium,
                trackColor = SplitTheme.colors.primary.backgroundWeak
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = stringResource(id = R.string.loading),
                style = SplitTheme.typography.body.l,
                color = SplitTheme.colors.neutral.textStrong
            )
        }
    }
}

@Composable
fun ErrorDialog(
    setShowDialog: (Boolean) -> Unit,
    errorTitle: String? = stringResource(id = R.string.generic_error_title),
    errorText: String? = stringResource(id = R.string.generic_error_text),
    errorButton: Int? = R.string.ok
) {
    Dialog(
        onDismissRequest = {
            setShowDialog(false)
        }
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = SplitTheme.colors.neutral.backgroundExtraWeak
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = errorTitle!!,
                    style = SplitTheme.typography.heading.l,
                    color = SplitTheme.colors.neutral.textTitle,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = errorText!!,
                    style = SplitTheme.typography.body.l,
                    color = SplitTheme.colors.neutral.textStrong,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(16.dp))
                PrimaryLargeButton(
                    onClick = { setShowDialog(false) },
                    text = errorButton!!
                )

            }

        }

    }
}

@Preview
@Composable
fun LoadingDialogPreview() {
    LoadingDialog()
}

@Preview
@Composable
fun ErrorDialogPreview() {
    ErrorDialog(setShowDialog = {})
}