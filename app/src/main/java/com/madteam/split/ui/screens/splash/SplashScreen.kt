package com.madteam.split.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madteam.split.R
import com.madteam.split.ui.theme.SplitTheme

@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = SplitTheme.colors.neutral.backgroundExtraWeak),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(200.dp),
            painter = painterResource(id = R.drawable.ds_split_logo),
            colorFilter = ColorFilter.tint(SplitTheme.colors.neutral.iconHeavy),
            contentDescription = stringResource(id = R.string.split_logo_description)
        )
        Spacer(modifier = Modifier.size(96.dp))
        CircularProgressIndicator(
            modifier = Modifier
                .size(50.dp),
            color = SplitTheme.colors.primary.backgroundMedium,
            trackColor = SplitTheme.colors.primary.backgroundWeak
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}