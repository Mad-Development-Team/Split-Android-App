package com.madteam.split.ui.theme

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.madteam.split.R
import kotlin.random.Random
import kotlin.random.nextInt

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
        BackHandler {
            //Do nothing on back press
        }
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
    onContinueClick: () -> Unit = {},
    errorTitle: String? = stringResource(id = R.string.generic_error_title),
    errorText: String? = stringResource(id = R.string.generic_error_text),
    errorButton: Int? = R.string.ok,
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
                    onClick = {
                        setShowDialog(false)
                        onContinueClick()
                    },
                    text = errorButton!!
                )
            }
        }
    }
}

@Composable
fun DangerDialog(
    setShowDialog: (Boolean) -> Unit,
    title: Int,
    text: Int,
    cancelButtonText: Int,
    continueButtonText: Int,
    onContinueClick: () -> Unit,
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
                    text = stringResource(id = title),
                    style = SplitTheme.typography.heading.l,
                    color = SplitTheme.colors.neutral.textTitle,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = stringResource(id = text),
                    style = SplitTheme.typography.body.l,
                    color = SplitTheme.colors.neutral.textStrong,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(16.dp))
                DangerLargeButton(
                    onClick = { onContinueClick() },
                    text = continueButtonText
                )
                Spacer(modifier = Modifier.size(16.dp))
                SecondaryLargeButton(
                    onClick = {
                        setShowDialog(false)
                    },
                    text = cancelButtonText
                )

            }

        }

    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun ChooseAvatarDialog(
    onChooseAvatar: (Int) -> Unit = {},
    onDismiss: () -> Unit = {},
    imagesList: List<String>,
) {
    val context = LocalContext.current
    val pagerState = rememberPagerState(
        pageCount = { imagesList.size },
        initialPage = if (imagesList.isEmpty()) {
            0
        } else {
            Random.nextInt(imagesList.indices)
        }
    )

    LaunchedEffect(pagerState.currentPage) {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
        if (imagesList.isNotEmpty()) {
            val vibrationEffect =
                VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)
        }
    }

    Dialog(
        onDismissRequest = {
            onDismiss()
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
                    text = stringResource(id = R.string.choose_an_avatar),
                    style = SplitTheme.typography.heading.l,
                    color = SplitTheme.colors.neutral.textTitle,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = stringResource(id = R.string.swipe_to_see_more),
                    style = SplitTheme.typography.body.m,
                    color = SplitTheme.colors.neutral.textTitle,
                    textAlign = TextAlign.Center
                )
            }
            if (imagesList.isNotEmpty()) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth(),
                ) { page ->
                    val image = imagesList[page]
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        GlideImage(
                            model = image,
                            contentDescription = null
                        )
                    }
                }
            } else {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.CenterHorizontally),
                    color = SplitTheme.colors.primary.backgroundMedium,
                    trackColor = SplitTheme.colors.primary.backgroundWeak
                )
            }
            Column(
                modifier = Modifier
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PrimaryLargeButton(
                    onClick = {
                        onChooseAvatar(pagerState.currentPage)
                    },
                    text = R.string.choose,
                    enabled = imagesList.isNotEmpty()
                )
                Spacer(modifier = Modifier.size(16.dp))
                SecondaryLargeButton(
                    onClick = {
                        onDismiss()
                    },
                    text = R.string.cancel
                )
            }
        }
    }
}

@Preview
@Composable
fun DangerDialogPreview() {
    DangerDialog(
        setShowDialog = {},
        title = R.string.is_it_a_goodbye,
        text = R.string.log_out_confirm_text,
        cancelButtonText = R.string.cancel,
        continueButtonText = R.string.continue_log_out,
        onContinueClick = {}
    )
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